# SearchableParamResolver

What is it?
- It's a custom Spring argument resolver that handles search functionality in REST APIs
- It converts the search query parameter into a SearchRequest object that can be used in controllers

Why is it needed?
1. Simplification: Makes search implementation cleaner and reusable
2. Standardization: Provides a consistent way to handle searches across your API
3. Convenience: Automatically converts URL parameters to usable Java objects
4. Clean Code: Reduces boilerplate in controllers

How it works:

1. Detection Method:
```java
@Override
public boolean supportsParameter(MethodParameter parameter) {
    return parameter.getParameterAnnotation(SearchableParam.class) != null;
}
```
- Checks if a method parameter has @SearchableParam annotation
- If true, Spring will use this resolver for that parameter

2. Parameter Resolution:
```java
String searchValue = webRequest.getParameter(annotation.searchParam());
Set<String> searchFields = new HashSet<>(Arrays.asList(annotation.searchFields()));
```
- Gets the search term from the URL parameter (default is "search")
- Gets the configured searchable fields from the annotation

3. Example Usage in Controller:
```java
@GetMapping("/products")
public Page<Product> getProducts(
    @SearchableParam(
        searchFields = {"name", "description"}
    ) SearchRequest search
) {
    // search.getSearchTerm() will contain the search value
    // search.getSearchFields() will contain ["name", "description"]
    return productService.search(search);
}
```

4. Example URL Patterns:
```
GET /products?search=phone           // Searches for "phone"
GET /products?search=samsung phone   // Searches for "samsung phone"
GET /products                        // No search term (returns all)
```

Key Features:
1. Flexible Configuration:
   ```java
   @SearchableParam(
       searchParam = "q",           // Custom parameter name
       searchFields = {"name", "description"}  // Fields to search
   )
   ```

2. Null Handling:
   ```java
   if (!StringUtils.hasText(searchValue)) {
       return new SearchRequest(null, searchFields);
   }
   ```
    - Returns a valid object even when no search term is provided

3. Input Cleaning:
   ```java
   return new SearchRequest(searchValue.trim(), searchFields);
   ```
    - Automatically trims whitespace from search terms

This resolver is particularly useful when:
1. You need consistent search behavior across multiple endpoints
2. You want to search across multiple fields
3. You want to keep your controller code clean and focused
4. You need flexible, configurable search functionality

It's part of a larger pattern for building maintainable and scalable APIs, working together with pagination and sorting to provide powerful data access capabilities.