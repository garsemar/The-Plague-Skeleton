# Pràctica UF4. POO Plague Game
{: .no_toc}

- Aquesta pràctica es realitza en parelles o de forma individual
- Creeu un projecte nou per fer aquesta pràctica
- Per entregar la pràctica afegeix el tag "practicaUF4" a la versió.
- Feu un fork i cloneu el projecte del git: https://gitlab.com/DAMW-M03-public/the-plague-skeleton



## Sumari
{: .no_toc}
1. TOC 
{:toc} 

## Pràctica: Plague
La pràctica consisteix en desenvolupar un joc anomenat Plague. 
En aquest document hi trobaràs:
- Les normes de joc i aclariments sobre les classes
- Projecte amb el codi, hi trobaràs implementada la UI i la GUI, i l'especificació d'algunes interfícies, necessàries per a què la UI i la GUI funcionin.
- El disseny UML

## PlagueGame 
Un científic especialitzat en contenció de plagues ha de controlar l'expansió d'unes plagues mortals que estan arrasant el món. Hauràs de viatjar pels diferents territoris, buscant recursos i erradicant les plagues sense sortir-ne il·lès.

És un joc en el què es tracta de mantenir el món sense plagues.
És un joc per torns en el que es van succeïnt les fases o accions següents:
- L'usuari mou el personatge a un territori que vol desinfectar.
- El personatge agafa recursos per desinfectar o viatjar
- fase de desinfecció
- fase de generació de noves plagues
- fase de generació de recursos o vehicles
- fase de reproducció de les colònies
- fase de d'expansió de les colònies

El jugador perd una vida cada cop que es produeix una expansió. El joc finalitza quan s'acaben les 15 vides del jugador.
L'objectiu és sobreviure tant temps com sigui possible. S'obté un punt per cada torn finalitzat

## UML

<img src="img/PlagueGame.drawio.png" style="width: 100%;"/>
-Les classes amb la capçalera gris ja estan implementades

[Consulteu l'UML a draw.io](https://drive.google.com/file/d/1eJuiDG1jbcq7xVSJ1kbYKV9VDccIMcTF/view?usp=sharing) 

** Aquest uml és un disseny previ, per tant no s'han especificat tots els mètodes o atributs

## Projecte Git
En el projecte clonat hi trobareu les interfícies d'usuari.
- La ui.console: és una interfície per consola
	- Pots executar-la amb el main de ConsoleMain
- La ui.gui: és una interfície gràfica.
	- Pots executar-la amb el main de GUIMain
- interfaces: Interfícies que definexen la comunicació entre la interfícies gràfica i la lògica.


## ConsoleMain & WorldUI
__Aquestes classes ja estan implementades i no s'han de tocar!__

__ConsoleMain__: Conté el main que inicia el joc. 

__WorldUI__: És la classe s'encarrega de la interfície gràfica i dirigeix la dinàmica del joc, que consisteix en gestionar les diferents rondes, mostrar el taulell, organitzar les accions del jugador, fer créixer les colònies.

A cada ronda o torn el jugador pot realitzar les accions següents:
- Moure el personatge.
- Agafar recursos per desinfectar les plagues més eficaçment o bé agafar un vehicle per moure's més ràpidament.
- Exterminar les plagues del territori on es troba, segons els recursos de què disposa. Vegeu l'apartat [Exterminar plagues](#exterminar-plagues)

Al final de cada ronda: 
- Es poden generar noves colònies.
- Les colònies es reprodueixen i s'expandeixen quan són grans (+3 icones)
- Apareixen nous recursos.
Vegeu l'apartat [next turn](#final-de-ronda---next-turn)


## World & Territory
__World__: El món està representat per un taulell en forma de matriu on cada una de les caselles és un territory.
Implementa una interfície IWorld, on hi trobareu els mètodes que cal que implementeu i la seva documentació. 

__Territory__ :
Un territory té un contador(plagueSize) que indica la grandària de la colònia (Aquest valor es consulta l'atribut size de la classe Colony).
plagueSize també ens indica el número d'icones de colònia que mostrarem: el valor màxim serà 3
Té 1 personatge o bé un recurs (ítem).
Vegeu el [disseny UML](#uml).
Cada territori només té un tipus de plaga, ja que una plaga invasora pot acabar amb una plaga existent, o bé una plaga existent pot evitar que arribi una plaga nova. Vegeu l'apartat [expansió](#expansió)

Aquesta classe implementa una interfície ITerritory, en la que s'especifiquen els mètodes que cal que implementeu i la seva documentació.



#### Output versió consola World(5,3)
```
-------------------------
|    |    |    |    |    |
|    |    |    |    |    |
-------------------------
|    |    |    |    |    |
|    |    |    |    |    |
-------------------------
|    |    |    |    |    |
|    |    |    |    |    |
-------------------------

```
Tingues en compte que els mètodes de la User interface que mostren les dades són al projecte adjunt, per exemple WorldUI.draw() pinta el taulell.

## Player
En el taulell hi col·locarem el jugador o personatge de l'usuari, la seva posició inicial serà al centre del taulell.
El jugador podrà tenir 1 arma de desinfecció i 1 vehicle. Vegeu el [disseny UML](#uml). 
Per defecte el jugador elimina les plagues amb la mà i es trasllada a peu.


## Exterminar plagues - estris de desinfecció (weapon)
Quan un personatge arriba a un territori intentarà exterminar la plaga amb un recurs de desinfecció (o arma). 

El territori decrementarà la seva població atacada.

Exterminar Plagues d'un territori:
- Dracs:
	- Escombra: no té cap efecte
	- Espasa: disminueix en 1 la mida de la colònia
	- Mà (sense armes): no té cap efecte <!-- el drac mata l'home, fi de la partida ? -->
- Formigues
	- Escombra: elimina la colònia
    - Espasa: decrementa en 1 la mida de la colònia
    - Mà (sense armes): decrementa en 2 la mida de la colònia

## Moure el personatge - vehicles
A l'inici de cada torn el personatge pot moure's, per defecte el personatge es desplaça a peu, això vol dir que, a cada moviment el personatge només és pot desplaçar als territoris contigus.
A la fase de generar nous ítems (next turn) poden apareixer vehicles al taulell, que el personatge podrà agafar, si es sitúa en el territori on s'ha generat, podrà escollir agafar-lo o no.

Aquest ítem apareix en un territori de manera aleatòria, i si ja hi havia un ítem desapareix i és reemplaçat pel nou ítem

Relació de moviments per vehicle

|  Vehicle         | Desplaçament |
| :--:             | :--:         |
| A peu       	   | A les caselles contigües         |
| Bicicleta 	   | A una distància de 4 caselles    |
| Helicopter 	   | A qualsevol lloc                 |


## Final de ronda - next turn
Al final de cada ronda es produeixen les fases següents:
__Assegura't de que et funciona tot abans d'implementar cada una de les fases__

### Generar noves colònies: 

Es genera 1 plaga en una casella aleatòria. La plaga serà d'un tipus o d'un altre escollit a l'atzar segons les probabilitats següents:

| Tipus de plaga | Probabilitat | 
| :--:           | :--:       |
| Formiga        | 30%  |
| Drac           | 10%  |

Només s'afegirà la nova colònia si el territori està sense colonitzar

### Generar nous ítems:
Pot aparèixer 1 nou recurs al taulell, escollit a l'atzar segons les probabilitats següents:

| Recurs o vehicle | Probabilitat | 
| :--:             | :--:         |
| Bicicleta 	   | 25%          |
| Helicopter 	   | 10%          |
| Escombra  	   | 25%          |
| Espasa    	   | 10%          |
| Res       	   | 30%          |

### Reproducció:
Les colònies existents a cada territori es reprodueixen, és a dir incrementen. 
Si hi havia 2 formigues -> Poden passar a ser 3 formigues

| Plaga    | Factor de reproducció  | 
| :--:     | :--:          		    |
| Drac 	   | 5 torns               |
| Formiga  | 30% possibilitats/ torn|

### Expansió:
Quan un territori sobpreassa la seva capacitat màxima de colònies (3), aquesta s'expandeix. Per exemple, un territori amb 3 dracs que s'ha reproduït, aquest quart drac s'expandeix a un nou territori. 
El nou territori és colonitzat si està buit, o bé si la plaga es considera més forta, si no s'extingeix.
Funció d'expansió i ordre de les colònies segons fortaleza 

| Plaga  |  S'expandeix a          | Colonització |
| :--:   | :--: 		           | :--: 		  |
| Drac 	 | Qualsevol casella       | Sempre       |
| Formiga| A les caselles contigües (Torre)| Només en territoris buits o de formigues |



## Icones
- personatge: 🚶
- colònia de formigues: 🐜
- plaga de dracs: 🐉
- vehicles o formes de viatjar: a peu:🚶, bicicleta: 🚲, helicopter: 🚁
- estris de desinfecció: mà: 👆, espasa:🗡, escombra: 🧹

## Canvis en el joc
Podeu modificar el funcionament del joc, així com crear noves plagues o estris i vehicles. Al fer-ho, heu de modificar també aquest document i el UML.

## Metodologia d'entrega
- Cal que el projecte contingui un markdown on indiqueu:
	- El nom i cognom dels autors
	- Els aspectes que funcionen 
	- Els aspectes que cal millorar
	- Els punts no desenvolupats

