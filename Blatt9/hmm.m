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
%transitions = transitions/(length(words)-1);
figure(); hold all;
surf(transitions);
title('Tag Transition Probabilities'); 
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
%observation_probs = observation_probs/(length(words)-1);

%figure(); hold all;
%surf(observation_probs);
%title('Observation probabilities')
%colorbar;





%%
% forward algorithm


%%
B = tdfread('hdt-10001-12000-test.tags');
testwords = cellstr(B.x0xEF0xBB0xBFDazu);
testtags = cellstr(B.PROAV);
observations = testwords;
real_tags = testtags;

%observations = {'An','der','Nasdaq','rutschte'	,'das'	,'Papier','am'};
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
    if isempty(ind_cur_obs )
        ind_cur_obs = word_vocabulary_length;
    end
    for i = 1:tag_vocabulary_length
        a(i,t) = a(:,t-1)' *  transitions(:,i) * observation_probs(i,ind_cur_obs);
        if norm(a(:,t)) == 0
            disp('error')
        end
    end
    a(:,t) = a(:,t)/norm(a(:,t));
    [val, ind ] = max(a(:,t));
    %disp({tag_vocabulary{ind} , cur_obs});
    
    prediction{t} = tag_vocabulary{ind};
end

%%
% compare arrays

correct_tags = sum(cellfun(@strcmp, prediction, real_tags));
error_tags = observation_length - correct_tags;


disp('Error in %: ');
(error_tags/observation_length)*100


