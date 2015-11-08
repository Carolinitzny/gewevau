#!/usr/bin/env python3
import sys, math
from queue import PriorityQueue

class Nodes(object):
    def __init__(self):
        self.blocked = False
        self.prev = None
        self.neighbours = []
        self.goal = False
        self.cost = None
        self.h = None
        self.pos = None

    def status(self):
        if self.blocked: return "x"
        if self.goal: return "g"
        if self.prev: return "b"
        return " "
    def __lt__(self, other):
        return True


def load_environment(filename):
    with open(filename, "r") as myfile:
        # Datei als einzelne Zeilen in ein Array einlesen  
        lines = myfile.readlines()
        
        # Endungen der Zeilen entfernen (Umbrüche) und Kleinschreibung
        lines = [line.replace("\n", "").lower() for line in lines]

        lines = [list(line) for line in lines]

        # Größe des Environments ermitteln
        width = len(lines[0])
        height = len(lines)
        
        # Für jedes Feld des Environments einen leeren Knoten erstellen
        env = [[Nodes() for x in range(width)] for y in range(height)]
        
        #Portale werden in ein Dictionary geschrieben.
        #Hierbei werden sie auf den selben Knoten gestetzt.
        portals = dict()
        for y in range(height):
            for x in range(width):
                symbol = lines[y][x]
                if symbol in "1234567890":
                    if symbol in portals:
                        env[y][x] = portals[symbol]
                    else:
                        portals[symbol] = env[y][x]



        
        start = None
        goal_pos = None
        #Variablen der Knoten belegen, hierbei die Position des Zielknotens merken, um im nächsten Durchlauf dann die Heuristik berechen
        #zu können
        for y in range(height):
            for x in range(width):
                symbol = lines[y][x]
                node = env[y][x]
                node.blocked = (symbol == "x")
                node.goal = (symbol == "g")
                node.pos = (x , y)
                if symbol == "s":
                    start = node
                if symbol == "g":
                    goal_pos = (x, y)
               
                #Enviroment wird gewrapped, der Knoten ganz links hat als linken Nachbarn den Knoten ganz rechts(ebenso mit oben/unten)
                #Dies ist möglich, da eine Umrandung des Feldes vorgegeben ist, die sowieso nicht überquert werden kann.
                
                #[x-1] tut schon das richtige, da Python bei negativem Index von hinten zählt.
                node.neighbours.append(env[y][x - 1])
                node.neighbours.append(env[y][(x + 1) % width])
                node.neighbours.append(env[y - 1][x])
                node.neighbours.append(env[(y + 1) % height][x])

        #Berechnen der Heuristik anhand der euklidischen Distanz. Wichtig ist, dass man die Position des Zielknotens vorher ermittelt hat!
        for y in range(height):
            for x in range(width):
                dx = x - goal_pos[0]
                dy = y - goal_pos[1]
                #Falls es Portale gibt, wird keine Heuristik verwendet --> der Algorithmus ist Breitensuche
                if portals:
                    h = 0
                else:
                    h = math.sqrt(dx ** 2 + dy ** 2)
                if env[y][x].h is None or env[y][x].h > h:
                    env[y][x].h = h
        #lines wird zurückgegeben, um später bei der ausgabe das Feld generieren zu können
        return start, lines, env



def astar(start, env):
    #frontier_inserstions zählt die Operationen, frontier_max_size die größte vorkommende Frontier
    frontier_insertions = 1
    frontier_max_size = 1
    start.cost = 0
    frontier = PriorityQueue()
    frontier.put((0, start))
    while frontier.qsize() != 0 :
        node = frontier.get()[1]
        #print("Visiting node " + node.name)
        #print("\n".join(["".join(node.status() for node in line) for line in env]))
        
        #Frontier erweitern, hierbei nur solche Knoten aufnehmen, die besucht werden dürfen (kein X) und noch nie in der Frontier waren
        #(ansonsten wäre cost gesetzt)
        for neighbour in node.neighbours:
            if neighbour.cost is None and not neighbour.blocked:
                neighbour.prev = node
                neighbour.cost = node.cost + 1
                frontier.put(( neighbour.cost + neighbour.h , neighbour))
                frontier_insertions += 1
                if frontier_max_size < frontier.qsize():
                    frontier_max_size = frontier.qsize()
                if neighbour.goal:
                    print("Time: {} Memory: {}".format(frontier_insertions, frontier_max_size))
                    return neighbour

    print("Time: {} Memory: {}".format(frontier_insertions, frontier_max_size))


#Fehlermeldung, falls der Skriptaufruf ohne Argument erfolgte (die Feld-Textdatei)
if len(sys.argv) < 2:
    print("Usage: {} FILE".format(sys.argv[0]))
    sys.exit(1)


start, field, env = load_environment(sys.argv[1])

goal = astar(start, env)
current = goal

#Backtrace. Vom Zielknoten an werden die Felder mit einem . markiert, die den besten Pfad bilden.

while current :
    x = current.pos[0]
    y = current.pos[1]
    field[y][x] = "."
    current = current.prev
#Printed das Field. Hierbei werden die einzelnen Lines aneinander gereit, dazwischen ein Zeilenumbruch
print("\n".join(["".join(line) for line in field]))

