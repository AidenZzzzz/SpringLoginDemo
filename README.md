# Spring Registration and Login shopping web backend

#project
The purpose of this project is to demonstrate my personal ability to construct spring boot powered web backend capable of handling user registration, user detail validation, login function and provide a generic shopping experience.

## Structure
![](i/Shopping.png)

## Application.yml config
```properties
server:
  error:
    include-binding-errors: always
    include-message: always
```
provides console log

```properties
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/login_demo
    username: root
    password: 1q2w3e4r
    driverClassName: com.mysql.cj.jdbc.Driver
  jpa:
    hibernate:
       ddl-auto: update
       #ddl-auto: create-drop
       #this destroys the db everytime the app is run.
    properties:
      hibernate:
        dialect: org.hibernate.dialect.MySQL5InnoDBDialect
        format_sql: true
    show-sql: true
```
datasource: database set
jpa. hibernate:
ddl-auto:

1. `update`: persistent database, do not reset after each startup

2. `create`, `create-drop`: not persistent database, data is lost each time the server reboot

3. `validate` : persistent do not create new table, but could insert new value


## Entity layer
There are 4 entities: AppUser, ConfirmationToken, Item and Cart

To declare a class to be an entity, use the `@Entitiy` Spring annotation at the top of the class.
```java 
@Entity
public class AppUser implements UserDetails {
    //imp UserDetail because we need the security

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
}
```

Other useful annotation:
`@Getter` `@Setter` give each entries in the class an getter/setter method
`@NoArgsConstructor` allows this class to instantiate with no constructer input
`@AllArgsConstructor` gives this class a constructer with all constructer input


`@Id` indicates this entry is the first param in the db
`@GeneratedValue(strategy = GenerationType.IDENTITY)` lets the following items have incremental ids

This tells the repository layer how to construct a persistent object in the database

```java
@Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //determine if the user is an admin or a reg user
        SimpleGrantedAuthority authority
                = new SimpleGrantedAuthority(appUserRole.name());
        return Collections.singleton(authority);
    }
```
Overide the above mo

## Repository Layer

Use `@Repository`  to declare this to be a repository layer and `@Transactional` to give it the necessary access to the data base.
```java
@Repository
@Transactional(readOnly = true)
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmail(String email);
}
```

we extend `JpaRepository` with the param< `VO` , `type of key`>

```java 
Optional<AppUser> findByEmail(String email);
```
The above interface provides the additional api for finding users with the same email in the method parameter. **It just works**.


## Controller layer

```java 

@RestController
@AllArgsConstructor
@RequestMapping(path = "/user/item")
public class ItemController {

    
```

`@RestController` tells spring this is a rest controller
`@AllArgsConstructor` provides convenient object init utils
`@RequestMapping` given path, this class handles all requests(Post Get... ) from http://localhost:8080/user/item/


then the addItem method will utilize repository to persistent data into the database

### Request

```java
 @PostMapping(path = "/add")
    public String addItem(@RequestBody AddItemRequest request){
        return itemService.addItem(request);
    }

```
continue from the `ItemController` class, by adding `@PostMapping` to the above method,  which will be called when a post request is sent. additional path variable could be append as http://localhost:8080/user/item/add

`@RequestBody` indicates the structure of the request, we define `AddItemRequest` as
```java
@AllArgsConstructor
@ToString
@Getter
public class AddItemRequest {
    private final String itemName;
    private final Integer quantity;
    private final Float price;
}
```


### Testing
When testing api's with postman, remember to add header option :
![](i/Screen%20Shot%202022-06-24%20at%2011.43.58.png)
this tells the sever we are sending a json object

Then we can send a json thru this post request on `user/item/add` mapping as
```json
{
    "itemName": "Cool pants",
    "quantity": 10,
    "price":29.99
}
```

## Service Layer

The service layer act as a logic layer to process request and manipulate database interface.
```java
@Service
@AllArgsConstructor
public class ItemService {
```

When an addItem request is received, the controller layer utilize the service layer method:
```java
public String addItem(AddItemRequest request) {

        boolean itemExist = itemRepository.findByItemName(request.getItemName()).isPresent();

        if(itemExist) {
            return request.getItemName() + " already exist, use another name";
        }

        Item item = new Item(
                request.getItemName(),
                request.getQuantity(),
                request.getPrice()
        );
        itemRepository.save(item);
        return "Works " + item.toString();
    }    
 
```
Along with the `request` content. Since we declared request object to be `AddItemRequest`, it has all the info we need.
In the is example, we want to check if there is any item with duplicate name. So we use the repository layer method `findByItemName` with `.isPresent()`
The repository layer has most of the functions prebuilt, but we still need to make declare the interface `findByName` in the `ItemRepository` class.
```java
Optional<Item> findByItemName(String itemName);
```
Note that the input param should be the same in the database




## Validation and Security


##Credit
>https://github.com/amigoscode/login-registration-backend/