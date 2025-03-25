# Entendendo a Flexibilidade do GraphQL vs. REST

Esclarecendo a diferença fundamental entre GraphQL e REST.

"Cada cliente pode pedir exatamente o que precisa sem exigir mudanças no backend" refere-se a:

1. **Estrutura dos dados retornados**: No GraphQL, os clientes podem especificar quais campos querem receber sem precisar de endpoints adicionais no backend.

   - **Exemplo**: Se um cliente mobile precisa apenas do `título` e `autor` do livro, enquanto a versão web precisa de `título`, `autor`, `ISBN` e `número de páginas`, o mesmo endpoint GraphQL serve para ambos.

2. **Agregação de dados**: O GraphQL permite juntar dados de múltiplas fontes em uma única requisição.

## O Caso da Busca por "Stone" - Por que Precisei Modificar

Você está absolutamente correto ao notar que para implementar a busca por "stone" no título, foi necessário:

1. Adicionar um método no repositório:
   ```java
   @Query("SELECT b FROM Book b WHERE LOWER(b.title) LIKE LOWER(CONCAT('%', :term, '%'))")
   List<Book> findByTitleContaining(@Param("term") String term);
   ```

2. Adicionar um resolver:
   ```java
   @QueryMapping
   public List<Book> booksByTitle(@Argument String title) {
       return bookRepository.findByTitleContaining(title);
   }
   ```

3. Atualizar o schema:
   ```graphql
   type Query {
       booksByTitle(title: String!): [Book]
   }
   ```

## Diferença Fundamental: Nível de Flexibilidade

| Cenário                     | REST                               | GraphQL                           |
|-----------------------------|------------------------------------|-----------------------------------|
| Busca por campo específico  | Precisa criar novo endpoint (`GET /books?title=stone`) | Estende o schema existente |
| Seleção de campos           | Retorna todos campos (ou precisa criar vários endpoints) | Cliente escolhe os campos |
| Relacionamentos             | Precisa de endpoints separados ou parâmetros complexos | Pede na mesma query |

## Por que Isso Ainda é uma Vantagem?

1. **Uma vez implementado, é mais flexível**:
   - Depois de ter o campo `booksByTitle`, os clientes podem combiná-lo com qualquer outro campo sem novas modificações no backend.

   Exemplo de query futura sem mudar o backend:
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

2. **Menos endpoints no longo prazo**:
   - Em REST, você acabaria com múltiplos endpoints para diferentes combinações (`/books`, `/books/search`, `/books-with-authors`, etc.)
   - Em GraphQL, você adiciona capacidades ao schema existente

## Quando Realmente Não Precisa Mudar o Backend

Para casos como:
```graphql
# Query 1
query {
  books {
    title
    author
  }
}

# Query 2 (mesmo endpoint, campos diferentes)
query {
  books {
    title
    isbn
    pageCount
  }
}
```

Nesses casos, o backend não precisa ser modificado para atender a diferentes necessidades de campos.

## Conclusão

- Para **novas capacidades** (como a busca por título), o backend precisa ser modificado
- A vantagem principal está na **flexibilidade de composição** após a capacidade ser adicionada

É uma diferença sutil mas importante: no GraphQL você geralmente precisa modificar o backend uma vez para habilitar muitas combinações possíveis de uso, enquanto no REST frequentemente precisa modificar para cada variação de uso.