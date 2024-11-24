# SortableParamResolver

What is it?
- It's a custom argument resolver in Spring MVC specifically for handling sorting parameters in API requests
- It converts URL query parameters into a list of SortRequest objects that can be used for database queries

Why is it needed?
1. Clean API Design: Allows clients to specify multiple sort criteria in a RESTful way
2. Validation: Ensures only allowed fields can be sorted
3. Standardization: Provides a consistent way to handle sorting across all endpoints
4. Reduces Boilerplate: Eliminates repetitive parameter parsing in controllers

How it works:

1. Detection of Sort Parameters:
```java
@Override
public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(SortableParam.class) != null;
}
```
- Checks if a method parameter has @SortableParam annotation
- If true, Spring will use this resolver

2. Example URL Patterns it handles:
```
/api/users?sort=name,age&order=asc,desc
/api/users?sort=createdAt
/api/users (uses defaults)
```

3. Parameter Processing:
```java
String sortValue = webRequest.getParameter(annotation.sortParam());
String orderValue = webRequest.getParameter(annotation.orderParam());

// Splits comma-separated values or uses defaults
String[] sortFields = Optional.ofNullable(sortValue)
        .map(s -> s.split(","))
        .orElse(new String[]{DEFAULT_SORT_FIELD});
```

4. Validation and Construction:
```java
List<String> allowedFields = Arrays.asList(annotation.allowedFields());

for (int i = 0; i < sortFields.length; i++) {
    String field = sortFields[i].trim();
    
    // Validates against allowed fields
    if (!allowedFields.isEmpty() && !allowedFields.contains(field)) {
        log.warn("Sort field '{}' is not allowed. Allowed fields: {}", field, allowedFields);
        continue;
    }
    // ... process direction and create SortRequest
}
```

Usage Example in Controller:
```java
@GetMapping("/users")
public List<User> getUsers(
    @SortableParam(
        allowedFields = {"id", "name", "age", "createdAt"}
    ) List<SortRequest> sortRequest
) {
    // sortRequest is automatically populated
    return userService.findAllSorted(sortRequest);
}
```

Key Features:
1. Multiple Sort Fields:
    - Handles multiple sort criteria (e.g., sort by name then age)
    - Each field can have its own sort direction

2. Default Values:
   ```java
   private static final String DEFAULT_SORT_FIELD = "id";
   private static final OrderDirection DEFAULT_DIRECTION = OrderDirection.DESC;
   ```
    - Used when no parameters are provided

3. Validation:
    - Validates sort fields against allowedFields
    - Validates sort directions (ASC/DESC)
    - Logs warnings for invalid inputs

4. Flexible Configuration:
   ```java
   @SortableParam(
       sortParam = "sort",      // customize parameter name
       orderParam = "order",    // customize parameter name
       allowedFields = {...}    // specify allowed fields
   )
   ```

Real-world Example:
```java
// URL: /api/products?sort=price,name&order=desc,asc

@GetMapping("/products")
public Page<Product> getProducts(
    @SortableParam(
        allowedFields = {"price", "name", "createdAt"}
    ) List<SortRequest> sortRequest
) {
    // sortRequest will contain:
    // 1. price DESC
    // 2. name ASC
    return productService.findAll(sortRequest);
}
```

This resolver is particularly useful when:
1. You need consistent sorting across multiple endpoints
2. You want to prevent sorting by unauthorized fields
3. You need to support multiple sort criteria
4. You want to keep your controller code clean and focused on business logic

It's part of a larger API design pattern that makes your endpoints more powerful and flexible while maintaining security and consistency.