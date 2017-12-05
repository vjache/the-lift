# Симулятор Лифта

Симулятор реализован ввиде стейт машины. Переходы между 
состояниями осуществляются на основе списка(агены) текущих 
команд лифту. При нажатии внешней(на этаже) или внутренней
(в самом лифте) кнопки, в агенду добавляется команда. 
По завершении каждого состояния, на его основе и текущей агенды 
выбирается следующее состояние и т.д. .

## Стейт машина
```
Rest 
    --> OpenCloseDoors
    --> MoveOneFloorDown
    --> MoveOneFloorUp
     
OpenCloseDoors 
    --> Rest
    --> MoveOneFloorDown
    --> MoveOneFloorUp
    
MoveOneFloorDown
    --> MoveOneFloorDown
    --> MoveOneFloorUp
    --> OpenCloseDoors
```