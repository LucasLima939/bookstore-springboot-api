# Accenture Java Project
Construção de uma API biblioteca com cadastro de usuário, livros e locações. A API pode ser acessada em https://apirest-biblioteca.herokuapp.com/swagger-ui.html

# Grupo na Minha Máquina Funciona
 - Lucas Amaral
 - Lucas Lima
 - Mauricio Santos

# Estrutura do Projeto
As classes do projeto foram divididas em pacotes de acordo com suas responsabilidades:

 - Model: os modelos das classes de objetos que usamos no sistema
 - Repository: onde definimos o JPA para acessar os dados do BD
 - Service: onde estão salvas as regras de negócio para manipulação dos Models
 - Resource: construção da API por meio da definição dos endpoints
 - Exception: onde definimos nossas exceções
 - Doc: onde definimos as configurações do Swagger para documentar a API
 - Security: onde definimos as configurações de segurança do Spring Security e JWT
 - Util: onde definimos o gerador da chave de segurança

# Cadastro
O model tem os atributos:

 - Id
 - Nome
 - Cpf
 - Email
 - Telefone
 - Login
 - Endereço
 - Cep

Tem os getters e setters dos atributos
Tem um construtor que já é criado o cadastro com o login e senha atribuida.
O service faz as validações para criação do usuário:

 - Campo nome,cpf e email são obrigatórios
 - Campo cpf deve conter 11 caracteres
 - Campos nome e email não devem passar de 50 caracteres
 - Campo telefone não deve passar de 20 caracteres
 - Não é possível cadastrar um cpf e login já existentes na base de dados
 - Ao passar um cep para o cadastro, o sistema realizada a consulta do endereço no site https://viacep.com.br e salva o endereço encontrado junto com o cadastro

# Cadastro Livro
O model tem os atributos:

 - Id
 - Isbn
 - Título
 - Valor da diária
 - Número de exemplares
 - Número de exemplares reservados
 - Tem o construtor que passa os atributos do isbn, titulo, valor da diaria e número de exemplares.
 - Tem os getters e setters dos atributos.
 - O service faz as validações para criação de um livro:
 - O campo isbn não deve passar de 50 caracteres
 - O campo título não deve passar de 100 caracteres
 - É obrigatório definir um valor de diária e a quantidade de números de exemplares
 - O campo número de exemplares reservados é escondido no cadastro do livro, sendo manipulável apenas na realização de uma locação
 - Não é possível realizar o cadastro de um livro que já existe no banco de dados

# ViaCepModel
O model tem os atributos:

 - cep
 - logradouro
 - complemento
 - bairro
 - localidade
 - uf
 - ibge
 - gia
 - ddd
 - siafi

Tem os getters e setters dos atributos.
O service realiza as validações para salvar os dados no ViaCepModel
 
 - Ao passar um cep para o cadastro de um usuário, o ViaCepService realiza a consulta do endereço no site https://viacep.com.br e salva as informações retornadas nos atributos de sua classe.
 
Endereço
O model tem os atributos:

 - Id
 - cep
 - logradouro
 - número
 - bairro
 - cidade
 - uf
 - ibge

O construtor é criado com os dados salvos no ViaCepModel sendo passados para os atributos da classe Endereço
 
 - Tem os getters e setters dos atributos 

# Locação
O model tem os atributos:

Id
Cadastro
Lista de livros
Data de agendamento
Data de retirada
Data de finalização
Valor total
Status Locação

Tem os getters e setters dos atributos
O service faz as validações para criação de uma locação:
Toda locação é iniciada com uma data de agendamento, Status da locação como Reservada e valor total = 0
Ao incluir uma lista de livros na locação, é verificado se o campo número de exemplares do livro é maior ou igual a 1
Ao incluir uma lista de livros na locação, é realizado o decremento da quantidade de número de exemplares e adicionado no campo número de exemplares reservados
Ao realizar uma retirada de uma locação, é inserido a data de retirada e alterado o status da locação para Efetivada
Ao finalizar a locação, é realizado o cálculo do número de diárias, subtraindo a data da devolução com a data da retirada. Após isso, é calculado o valor total da locação, sendo realizado o cálculo com o número de diárias * valor da diária de todos os livros que estão na locação.
Ao finalizar a locação, é inserido a data de finalização e alterado o status da locação para Finalizada
Login
O model tem os seus atributos:

Login
Senha

Tem os getters e setters dos atributos

O service faz as validações para criação de um login
Os campos login e senha não devem ser vazios
É realizado a verificação da senha informada é a mesma da senha cadastrada ao usuário
Ao realizar login é iniciada uma sessão com um token de expiração
Sessão
O model tem os seus atributos:

Login
Token
Data início
Data fim

Tem os getters e setters dos atributos

O service faz as validações para criação de um login
Ao realizar login, gerado um token de acesso e inserido a data inicio da consulta e uma data fim de expiração do login
Telefone
O model tem seus atributos:

Cadastro
numero
Telefone tipo

 Tem os getters e setters dos atributos
StatusLocação Enum
RESERVADA
EFETIVADA
FINALIZADA

TelefoneTipo Enum
WHATSAPP
FIXO
COMERCIAL
Spring Boot
A API será hospedada no Swagger para apresentação do projeto final e checagens posteriores realizadas por terceiros

Funcionalidades da API
Usuário
Criar usuario
logar
Consultar usuário
Livro
Criar livro
Consultar livro
Locação
Agendar locação
Retirar locação
Finalizar locação

Segurança
A API usa o Json Web Token (JWT) para autenticação dos endpoints durante o seu uso e o Spring Security para configurações internas da API.


