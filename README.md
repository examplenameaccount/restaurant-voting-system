Restaurant voting rest application
===================
#### A system for deciding which restaurant to have lunch at.

The application functionality is based on REST principles and realizes REST API without frontend.
- 2 types of users: admin and regular users
- Basic authentication
- Admins can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price), admins do the updates of menus
- Only admins can create new admin account
- Users can vote on which restaurant they want to have lunch at
- Only one vote counted per user
- If user votes again the same day:
  - If it is before 11:00 we assume that he changed his mind.
  - If it is after 11:00 then it is too late, vote can't be changed.
  
### **Main used technologies are:**
  - Hibernate
  - Spring MVC
  - Spring Rest
  - Spring Security
  - Spring Data JPA

### Restorant handling

#### Get all restaurants:
    curl -s  http://localhost:8079/rest/restaurants --user admin@gmail.com:admin

#### Get restaurant with id 100005:
    curl -s http://localhost:8079/rest/restaurants/100005 --user admin@gmail.com:admin

#### Create new restaurant:
    curl -s -X POST -d '{"name":"New Restaurant"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8079/rest/restaurants --user admin@gmail.com:admin

#### Update restaurant:
    curl -s -X PUT -d '{"name":"Update Name"}' -H 'Content-Type: application/json' http://localhost:8079/rest/restaurants/100006 --user admin@gmail.com:admin

#### Delete restaurant:
    curl -s -X DELETE http://localhost:8079/rest/restaurants/100006 --user admin@gmail.com:admin

### Menu handling:

#### Get all menus for restaurant with id 100005:
    curl -s http://localhost:8079/rest/restaurants/100005/menus --user admin@gmail.com:admin`

#### Get menu with id 100008:
    curl -s http://localhost:8079/rest/menus/100008 --user admin@gmail.com:admin

#### Create new menu:
    curl -s -X POST -d '{"dateTime": "2020-12-12", "name": "New Menu"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8079/rest/restaurants/100007/menu --user admin@gmail.com:admin

#### Update menu:
    curl -s -X PUT -d '{"dateTime": "2020-11-11", "name": "New Menu", "restaurant": {"id": 100005}}' -H 'Content-Type: application/json' http://localhost:8079/rest/menus/100010 --user admin@gmail.com:admin

#### Delete menu:
    curl -s -X DELETE http://localhost:8079/rest/menus/100008 --user admin@gmail.com:admin

### Course handling:

#### Get all courses for menu with id 100010:
    curl -s http://localhost:8079/rest/menus/100010/courses --user admin@gmail.com:admin

#### Get course with id 100016:
    curl -s http://localhost:8079/rest/courses/100016 --user admin@gmail.com:admin

#### Create new course:
    curl -s -X POST -d '{"name":"New Course", "price": 25}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8079/rest/menus/100010/course --user admin@gmail.com:admin

#### Update course:
    curl -s -X PUT -d '{"menu": {"id": 100010}, "name": "Update Course", "price": 30}' -H 'Content-Type: application/json' http://localhost:8079/rest/courses/100016 --user admin@gmail.com:admin

#### Delete course:
    curl -s -X DELETE http://localhost:8079/rest/courses/100016 --user admin@gmail.com:admin

### Vote handling:

#### Vote for restaurant with id
    curl -s -X POST http://localhost:8079/rest/restaurants/100005 --user dmitriy@yandex.ru:ytrewq1234

User handling:

#### Get all Users
    curl -s http://localhost:8080/rest/admin/users --user admin@gmail.com:admin

#### get Users 100001
    curl -s http://localhost:8080/rest/admin/users/100001 --user admin@gmail.com:admin

#### Validate with Error
    curl -s -X POST -d '{}' -H 'Content-Type: application/json' http://localhost:8080/rest/admin/users --user admin@gmail.com:admin
