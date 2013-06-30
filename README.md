Project name
------------

  Luxury menza for hungry students

Co-workers
----------
  Zdeněk Tuháček
  Michal Šik
  Petr Kukrál

Project description
-------------------

Luxury Menza is J2EE project which implements application for managing student's restaurant via information system. 
We implemented this semestral project into MDW - Middleware and Web services subject. System is capable of following 
activities based on user roles:

    User role: Boarder - student, professor or university friend who like eating in menza
        Activities:
            Edit boarder account - update his personal identifications and bank accounts
            Order menucard - list available menucards and order selected food with specified date and number of portion
            Order reservation - specify date, time and number of reservation seats
            
    User role: Manager - menza staff who is able to operate within information system on user accounts
        Activities:
            View menza user account - list available information system users including their roles, 
                                      date of addition etc.
            Edit menza user account - create new users with their assignment to system roles, 
                                      edit and delete all available system users
            Edit boarder account - list boarder users and set their state to active or deactivate, 
                                   each user could be also deleted from system
            Edit reservation - list ordered reservations, create new reservations for specified user, 
                               edit and delete created reservations
            
    User role: Chef - menza staff who is responsible for menu
        Activities:
            Edit menucard - list created menucards, create new menucard for specified day, 
                            edit and delete specified menucard
            
    User role: Issuer - menza staff who is responsible for set up food for current boarder in queue 
                        depending on ordered menucard
        Activities:
            not implemented
        
    User role: Robot - light panel which displays available menu for current day
        Activities:
            not implemented
        
    User role: All - any internet user with or withour menza account, he is able to consume web services 
                     from neighbour sites
        Activities:
            Identity checking - display user identification and his assigned roles
            Skladiste - list all items in warehouse, create new items in warehouse, 
                        edit and delete specified item from warehouse
            Restaurant - list all reservations, create new reservation, delete specified reservation

Implementation details
----------------------

  Implementation is based on following technologies:

    Servlets
    JSP
    JDO
    RESTEasy framework
    Google app engine

Links
-----

You can visit information system Luxury Menza via link  http://fit-mdw-ws10-102-4.appspot.com/
Source codes are available via git on site              https://github.com/kukisak/menza


