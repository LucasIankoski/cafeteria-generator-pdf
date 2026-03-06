# cantina-pdf

Gerador de PDF para controle de cantina por período letivo.

O projeto cria um PDF A4 com:
- cabeçalho da cantina
- campos em branco para Estudante e Turma
- mini-calendários mensais por período
- linha de assinatura

## Requisitos

- Java 17+
- Maven 3.8+

## Arquivos principais

- `src/main/java/org/example/PdfGenerator.java`: lógica de geração do PDF.
- `logo_vettorello.PNG`: logo usado no cabeçalho.
- `pom.xml`: dependências (iText 7) e configuração Maven.

## Como gerar o PDF

No diretório do projeto, execute:

```powershell
mvn -q -DskipTests compile
mvn --% -q exec:java -Dexec.mainClass=org.example.PdfGenerator
```

Arquivo gerado (padrão):
- `lista_de_alunos_turma.pdf`

## Customização rápida

Edite constantes/trechos em `PdfGenerator.java`:

- Nome do arquivo de saída:
  - `DEST`
- Caminho da logo:
  - `LOGO_PATH`
- Quantidade de fichas por período (cartões):
  - `CARDS_PER_TERM`
- Períodos e meses:
  - lista `terms` no método `main`
  - exemplo atual:
    - `2026/I`: março a julho
    - `2026/II`: agosto a dezembro

## Observações

- Se a logo não aparecer, confirme se `logo_vettorello.PNG` está na raiz do projeto.
- Se quiser preencher nome/turma manualmente, mantenha `student` e `classCode` vazios no `main`.