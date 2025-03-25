# Java + Spring - GraphQL 

## GraphQL

GraphQL is a modern query language and runtime for APIs that provides a more efficient and flexible alternative to traditional REST architectures. Developed by Facebook in 2012 and open-sourced in 2015, it allows clients to request exactly the data they need in a single request, solving common problems like over-fetching (receiving unnecessary data) and under-fetching (not getting enough data in one request).

Unlike REST which uses fixed endpoints that return predetermined data structures, GraphQL uses a single endpoint and lets clients specify their data requirements through queries. These queries describe the desired data shape and relationships, and the server responds with JSON data matching exactly what was requested. This approach is particularly valuable for applications with complex data requirements or multiple clients (web, mobile, etc.) that need different data views of the same information.

At its core, GraphQL consists of three main components: a type system that defines your API's capabilities, query language for requesting data, and a resolver system that fulfills those queries with data. It supports not just data fetching (queries) but also modifications (mutations) and real-time updates (subscriptions). This makes GraphQL especially powerful for modern applications that need to combine data from multiple sources while providing excellent developer experience through strong typing and introspection capabilities.

## Maven 

### Install packages

`mvn clean install`

### Run
`mvn spring-boot:run`

## Access Graphiql (Development)

`http://localhost:8080/graphiql`

### Create Author

```graphql
mutation {
  createAuthor(name: "J.K. Rowling", age: 55) {
    id
    name
    age
  }
}
```

### Create Book

```graphql
mutation {
  createBook(
    title: "Harry Potter and the Philosopher's Stone",
    isbn: "978-0747532743",
    pageCount: 223,
    authorId: 1
  ) {
    id
    title
    author {
      name
    }
  }
}
```

### Search Book

```graphql
query {
  allBooks {
    id
    title
    author {
      name
      books {
        title
      }
    }
  }
}
```

### Count Books

```graphql
query {
  allBooks {
    id
  }
}
```


### Check the first 10

```graphql
query {
  allBooks(first: 10) {
    title
    author {
      name
    }
  }
}
```

### Count by Author

```graphql
query {
  allAuthors {
    name
    books {
      id
    }
  }
}
```