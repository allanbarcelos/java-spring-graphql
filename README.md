# Java + Spring - GraphQL 

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