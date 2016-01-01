clear; close all;

A = tdfread('hdt-1-10000-train.tags');

%%
words = cellstr(A.Konkursger0xC30xBCchte);
tags = cellstr(A.NN);

%reduces runnign taglist to unique tags, tag_index contains tag_indexes of
%running list to vocab
[tag_vocabulary, void, tag_index] = unique(tags);
tag_vocabulary_length = length(tag_vocabulary); 

%counts occurences of each word
tag_frequencies = hist(tag_index,tag_vocabulary_length);

%normalize occurences to get apriori
apriori = (tag_frequencies/length(tags))';


%%
% calc transition probs
transitions = zeros(tag_vocabulary_length,tag_vocabulary_length);
for i = 2:length(tags)
    fromTag = tag_index(i-1);
    toTag = tag_index(i);
    transitions(fromTag,toTag) = transitions(fromTag,toTag)+1;
end


figure(); hold all;
surf([transitions;zeros(1,tag_vocabulary_length)]);
title('Tag Transition Probabilities'); 
set(gca,'YTick',(1:tag_vocabulary_length),'YTickLabel',tag_vocabulary);
set(gca,'XTick',(1:tag_vocabulary_length),'XTickLabel',tag_vocabulary);

colorbar;


%%
%calc observation probs
[word_vocabulary, void, word_index] = unique(words);
tag_vocabulary = [tag_vocabulary;  'unknown' ]; %add one tag as state for unknow words
word_vocabulary_length = length(word_vocabulary);


observation_probs = ones(tag_vocabulary_length,word_vocabulary_length)/10;
observation_probs(:,end) = 1;
for i = 2:length(words)
    fromTag = tag_index(i);
    toWord = word_index(i);
    observation_probs(fromTag,toWord) = observation_probs(fromTag,toWord)+1;
end






%% forward algorithm

% use testfile as input for forward algorithm
%B = tdfread('hdt-10001-12000-test.tags');
%testwords = cellstr(B.x0xEF0xBB0xBFDazu);
%testtags = cellstr(B.PROAV);
%observations = testwords(1:100);
%real_tags = testtags(1:100);

% uncomment this code to use manual input
observations = {'An','der','Nasdaq','rutschte'	,'das'	,'Papier','am'};
real_tags = {'APPR';'ART';'NE';'VVFIN';'ART';'NN';'APRART'};
%'An'APPR
%'der'	ART
%'Nasdaq'	NE
%'rutschte'	VVFIN
%'das'	ART
%'Papier'	NN
%'am'	APPRART
observation_length = length(observations);

a = zeros(tag_vocabulary_length,observation_length);


prediction = cell(observation_length,1);
unknown = zeros(observation_length,1);

%Initialisierung
cur_obs = observations{1};
ind_cur_obs = find(ismember(word_vocabulary,cur_obs)); %returns index of current observed word within the vocab
a(:,1) = apriori .* observation_probs(:,ind_cur_obs);

[val, ind ] = max(a(:,1));
%disp({tag_vocabulary{ind} , cur_obs});

prediction{1} = tag_vocabulary{ind} ;

%Rekursion
for t = 2:observation_length
    cur_obs = observations{t};
    ind_cur_obs = find(ismember(word_vocabulary,cur_obs)); %returns index of current observed word within the vocab
    if isempty(ind_cur_obs ) %if word is not found in obs list, the index is set to the collumn with same prob for each state
        ind_cur_obs = word_vocabulary_length;
        unknown(t) = 1;
    end
    
    %calculate a(t)
    a(:,t) =  (a(:,t-1)' *  transitions) .* observation_probs(:,ind_cur_obs)'; 
    %normalize a(t)
    a(:,t) = a(:,t)/norm(a(:,t));

    %choose tag with max value in a(t) as current tag
    [val, ind ] = max(a(:,t));    
    prediction{t} = tag_vocabulary{ind};
end

%%
% compare prediction and actual tags
correct_tags = sum(cellfun(@strcmp, prediction, real_tags));
error_tags = observation_length - correct_tags;


disp('Error in %: ');
(error_tags/observation_length)*100


%% Display a(t). Each collumn responds to one timestep/word.
% If only one filed per collumn is yellow, the decision was quite clear, if
% many are colored, the decision was unclear.
figure(); hold all;
surf([a;zeros(1,observation_length)]);
%colormap cool;
colorbar;
xlabel('Words');
ylabel('Tags');
title('Matrix a, value of each state in %');
set(gca,'YTick',(1:tag_vocabulary_length),'YTickLabel',tag_vocabulary);

figure(); hold all;
plot(cellfun(@strcmp, prediction, real_tags));
plot(mean(a));
plot(var(a));
plot(unknown);
legend('classification: true = 1, false = 0',...
    'mean of "a" matrix', ...
    'variance of "a" matrix', ...
    'word unknown: true = 1, false = 0');
