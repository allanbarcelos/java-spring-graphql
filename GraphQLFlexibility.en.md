# Understanding GraphQL Flexibility vs. REST

Clarify the fundamental difference between GraphQL and REST.

## What the Original Statement Meant

"Each client can request exactly what they need without requiring backend changes" refers to:

1. **Response structure**: In GraphQL, clients can specify which fields they want to receive without needing additional backend endpoints.

   - **Example**: If a mobile client only needs a book's `title` and `author`, while the web version needs `title`, `author`, `ISBN` and `pageCount`, the same GraphQL endpoint serves both.

2. **Data aggregation**: GraphQL allows combining data from multiple sources in a single request.

## The "Stone" Search Case - Why Modifications Were Needed

You're absolutely correct to notice that implementing title search required:

1. Adding a repository method:
   ```java
   @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :term, '%'))")
   List<Book> findByTitleContaining(@Param("term") String term);
   ```

2. Adding a resolver:
   ```java
   @QueryMapping
   public List<Book> booksByTitle(@Argument String title) {
       return bookRepository.findByTitleContaining(title);
   }
   ```

3. Updating the schema:
   ```graphql
   type Query {
       booksByTitle(title: String!): [Book]
   }
   ```

## Key Difference: Flexibility Level

| Scenario                  | REST                               | GraphQL                           |
|--------------------------|------------------------------------|-----------------------------------|
| Field-specific search    | Needs new endpoint (`GET /books?title=stone`) | Extends existing schema |
| Field selection          | Returns all fields (or needs multiple endpoints) | Client chooses fields |
| Relationships            | Requires separate endpoints or complex parameters | Fetched in same query |

## Why This is Still an Advantage

1. **More flexible once implemented**:
   - After adding `booksByTitle`, clients can combine it with any other fields without new backend changes.

   Example future query without backend changes:
   ```graphql
   query {
     booksByTitle(title: "stone") {
       title
       author {
         name
         otherBooks {
           title
         }
       }
       reviews {
         rating
         comment
       }
     }
   }
   ```

2. **Fewer endpoints long-term**:
   - REST would require multiple endpoints for different combinations (`/books`, `/books/search`, `/books-with-authors`, etc.)
   - GraphQL adds capabilities to the existing schema

## When Backend Really Doesn't Need Changes

For cases like:
```graphql
# Query 1
query {
  books {
    title
    author
  }
}

# Query 2 (same endpoint, different fields)
query {
  books {
    title
    isbn
    pageCount
  }
}
```

In these cases, the backend doesn't need modification to serve different field requirements.

## Conclusion

- For **new capabilities** (like title search), the backend needs modification
- The main advantage lies in **composition flexibility** after the capability is added

It's a subtle but important difference: with GraphQL you typically modify the backend once to enable many possible usage combinations, while with REST you often need to modify for each usage variation.