# ✨ ETL com Apache POI em Java ✨

Bem-vindo ao repositório do nosso incrível projeto de ETL (Extração, Transformação e Carga) desenvolvido em **Java**, utilizando a poderosa biblioteca **Apache POI** para manipulação de arquivos Excel (.xlsx). Nosso objetivo é extrair dados de planilhas, realizar a limpeza e armazenar as informações em um banco de dados **MySQL** de forma eficiente e escalável! 🚀


![image](https://github.com/user-attachments/assets/79005e60-eb80-4557-a19d-4bb0cc3ee6f6)

## 🛠 Tecnologias Utilizadas

- 💻 **Java**
- 📄 **Apache POI** (para leitura de arquivos Excel)
- 🛢 **MySQL** (para armazenamento dos dados)
- 🔌 **JDBC** (para conexão com o banco de dados)
- 🏗 **IntelliJ IDEA** (IDE utilizada no desenvolvimento)

## 🚧 Status do Projeto

📌 O projeto ainda está em desenvolvimento, mas já estamos trabalhando para trazer:
- 🔹 Implementação de tratamento de erros e logs.
- 🔹 Melhorias na performance do processamento de grandes volumes de dados.
- 🔹 Interface intuitiva para visualização dos dados extraídos.

## 🚀 Como Configurar o Projeto

1. **Clone o repositório**
   ```sh
   git clone https://github.com/seuusuario/seurepositorio.git
   cd seurepositorio
   ```
2. **Importe o projeto no IntelliJ IDEA** 🏗
3. **Configure o banco de dados** 🛢
   - Certifique-se de ter um banco de dados MySQL rodando.
   - Crie uma base de dados com o nome desejado.
   - Atualize as credenciais no arquivo `DatabaseLoader` localizado em `etl/load/DatabaseLoader.java`:
     ```java
     String url = "jdbc:mysql://localhost:3306/seubanco";
     String username = "seuusuario";
     String password = "suasenha";
     ```
4. **Instale as dependências** 📦
   - Adicione a biblioteca Apache POI ao seu projeto (caso use Maven, inclua no `pom.xml`):
     ```xml
     <dependencies>
         <dependency>
             <groupId>org.apache.poi</groupId>
             <artifactId>poi-ooxml</artifactId>
             <version>5.2.3</version>
         </dependency>
     </dependencies>
     ```
5. **Execute o projeto** ▶️
   - Rode a classe principal que inicia a extração e carga dos dados.

## 📂 Estrutura do Projeto

```
/seurepositorio
├── src
│   ├── etl
│   │   ├── extract  # 📥 Módulo de extração de dados do Excel
│   │   ├── transform # 🔄 Módulo de limpeza e transformação dos dados
│   │   ├── load     # 📤 Módulo de carga dos dados para o banco
│   │   │   ├── DatabaseLoader.java  # ⚙️ Configuração de conexão com o banco
├── README.md  # 📖 Documentação do projeto
├── pom.xml  # 📜 Dependências do projeto (caso use Maven)
```

## 🤝 Como Contribuir

Se deseja contribuir com melhorias, siga os passos abaixo:
1. 🍴 Faça um fork do repositório
2. 🌿 Crie um branch para suas alterações (`git checkout -b minha-feature`)
3. 📌 Commit suas mudanças (`git commit -m 'Adicionando nova feature'`)
4. 🚀 Envie para o repositório (`git push origin minha-feature`)
5. 🔁 Abra um Pull Request

## 📜 Licença

Este projeto está sob a licença **MIT**. Sinta-se livre para usar e modificar conforme necessário. 😊



