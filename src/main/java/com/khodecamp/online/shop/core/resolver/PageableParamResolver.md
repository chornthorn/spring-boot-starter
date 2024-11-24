# PageableParamResolver

What is it?
- This is a custom argument resolver in Spring MVC that handles pagination parameters in REST API requests
- It implements HandlerMethodArgumentResolver interface, which allows Spring to automatically resolve method parameters in controller methods

Why is it needed?
1. Standardization: Provides a consistent way to handle pagination across your API endpoints
2. Convenience: Developers don't need to manually parse page/limit parameters in each controller
3. Validation: Handles parameter validation and applies defaults automatically
4. Clean Code: Reduces boilerplate code in controllers

How does it work?

1. Parameter Detection:
```java
@Override
public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(PageableParam.class) != null;
}
```
- Checks if a method parameter is annotated with @PageableParam
- If true, Spring will use this resolver for that parameter

2. Parameter Resolution:
```java
@Override
public Object resolveArgument(MethodParameter parameter,
                              ModelAndViewContainer mavContainer,
                              NativeWebRequest webRequest,
                              WebDataBinderFactory binderFactory) {
    // ... resolution logic ...
}
```
- Gets called when Spring needs to resolve a parameter marked with @PageableParam
- Extracts page and limit values from the request parameters
- Applies validation and default values

Example Usage in a Controller:
```java
@GetMapping("/items")
public Page<Item> getItems(@PageableParam PageRequest pageRequest) {
    // pageRequest is automatically populated with validated page/limit values
    return itemService.findAll(pageRequest);
}
```

Request Examples:
1. GET /items?page=2&limit=10
2. GET /items (uses default values)
3. GET /items?page=1&limit=50 (limit will be capped at maxLimit)

Key Features:
1. Default Values: Uses configuration-defined defaults if parameters are missing
2. Validation:
    - Ensures page numbers are positive
    - Ensures limit is between 1 and maxLimit
    - Handles invalid number formats gracefully
3. Logging: Warns about invalid parameter values
4. Configuration: Uses PaginationConfig for defaults and limits

This resolver is part of a larger pagination system that helps implement proper pagination in your REST APIs, making them more efficient and easier to use.