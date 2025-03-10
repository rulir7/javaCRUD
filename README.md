Agora você tem todas as funcionalidades solicitadas implementadas! Aqui está um resumo dos endpoints disponíveis para gerenciar os comentários:
Listar os Posts com seus comentários:
GET /posts - Lista todos os posts com seus comentários
GET /posts/{id} - Mostra um post específico com seus comentários

Listar comentários de um Post:
GET /posts/{postId}/comments - Lista todos os comentários de um post específico
GET /comments/{commentId} - Mostra um comentário específico

Editar um comentário:
PUT /comments/{commentId} - Atualiza o conteúdo de um comentário

Deletar um comentário:
DELETE /comments/{commentId} - Remove um comentário específico

Para testar as novas funcionalidades, você pode usar as seguintes requisições de exemplo:
Criar um comentário em um post:
POST /posts/{postId}/comments
{
    "content": "Ótimo post!",
    "postId": "UUID-DO-POST"
}

Editar um comentário:
PUT /comments/{commentId}
{
    "content": "Conteúdo atualizado do comentário",
    "postId": "UUID-DO-POST"
}

Listar comentários de um post:
GET /posts/{postId}/comments

Deletar um comentário:
DELETE /comments/{commentId}

O sistema agora suporta todas as operações CRUD para comentários, e os comentários estão vinculados aos posts através de uma relação ManyToOne. Quando um post é deletado, todos os seus comentários são automaticamente removidos devido à configuração cascade = CascadeType.ALL e orphanRemoval = true.
