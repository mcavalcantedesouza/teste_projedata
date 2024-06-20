import industria.Funcionario;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        //Funcionários da tabela adicionados
        funcionarios.add(new Funcionario("Maria", LocalDate.parse("18/10/2000", formatter), new BigDecimal("2009.44"), "Operador" ));
        funcionarios.add(new Funcionario("Joao", LocalDate.parse("12/05/1990", formatter), new BigDecimal("2284.38"), "Operador"));
        funcionarios.add(new Funcionario("Caio", LocalDate.parse("02/05/1961", formatter), new BigDecimal("9836.14"), "Coordenador"));
        funcionarios.add(new Funcionario("Miguel", LocalDate.parse("14/10/1988", formatter), new BigDecimal("19119.88"), "Diretor"));
        funcionarios.add(new Funcionario("Alice", LocalDate.parse("05/01/1995", formatter), new BigDecimal("2234.68"), "Recepcionista"));
        funcionarios.add(new Funcionario("Heitor", LocalDate.parse("19/11/1999", formatter), new BigDecimal("1582.72"), "Operador"));
        funcionarios.add(new Funcionario("Arthur", LocalDate.parse("31/03/1993", formatter), new BigDecimal("4071.84"), "Contador"));
        funcionarios.add(new Funcionario("Laura", LocalDate.parse("08/07/1994", formatter), new BigDecimal("3017.45"), "Gerente"));
        funcionarios.add(new Funcionario("Heloisa", LocalDate.parse("24/05/2003", formatter), new BigDecimal("1606.85"), "Eletricista"));
        funcionarios.add(new Funcionario("Helena", LocalDate.parse("02/09/1996", formatter), new BigDecimal("2799.93"), "Gerente"));

        //Removendo funcionário Joao da tabela
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("Joao"));

        //Atualizando os valores dos funcionários com o aumento de 10%
        funcionarios.forEach(funcionario -> {
            BigDecimal salarioAtual = funcionario.getSalario();
            BigDecimal novoSalario = salarioAtual.multiply(new BigDecimal("1.10"));
            funcionario.setSalario(novoSalario);
        });

        //Imprimindo todos os funcionários
        funcionarios.forEach(funcionario -> {
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
            System.out.println("Salário: " + String.format(Locale.GERMAN, "%,.2f", funcionario.getSalario()));
            System.out.println("Função: " + funcionario.getFuncao());
            System.out.println();
        });

        //criando map para agrupar os funcionários por função
        Map<String, List<Funcionario>> listarPorFuncao = new HashMap<>();

        //agrupando funcionários por função
        funcionarios.forEach(funcionario -> {
            listarPorFuncao.computeIfAbsent(funcionario.getFuncao(), k -> new ArrayList<>()).add(funcionario);
        });


        //imprimindo funcionários agrupados por função
        System.out.println("==Funcionários agrupados por função==");
        listarPorFuncao.forEach((funcao, listaFuncionarios) -> {
            System.out.println("== " + funcao + " ==");
            listaFuncionarios.forEach(f -> {
                System.out.println("Nome: " + f.getNome());
                System.out.println("Data de Nascimento: " + f.getDataNascimento().format(formatter));
                System.out.println("Salário: " + String.format(Locale.GERMAN, "%,.2f", f.getSalario()));
                System.out.println();
            });
        });

        System.out.println("==Funcionários que fazem aniversário nos meses 10 e 12.==");
        funcionarios.forEach(funcionario -> {
            int mesNascimento = funcionario.getDataNascimento().getMonthValue();
            if (mesNascimento == 10 || mesNascimento == 12){
                System.out.println("Nome: " + funcionario.getNome());
                System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
                System.out.println("Função: " + funcionario.getFuncao());
                System.out.println("Salário: " + String.format(Locale.GERMAN, "%,.2f", funcionario.getSalario()));
                System.out.println();
            }
        });

        Funcionario maiorIdade = funcionarios.stream()
                .min((f1,f2)-> f1.getDataNascimento().compareTo(f2.getDataNascimento()))
                .orElse(null);

        System.out.println("==Funcionario de maior idade==");
        if (maiorIdade != null) {
            int idade = LocalDate.now().getYear() - maiorIdade.getDataNascimento().getYear();
            System.out.println("nome: " + maiorIdade.getNome());
            System.out.println("idade: " + idade);
            System.out.println();
        }


        System.out.println("==Funcionários em ordem alfabética==");
        funcionarios.stream()
                .sorted((f1,f2) -> f1.getNome().compareTo(f2.getNome()))
                .forEach(funcionario -> {
                    System.out.println("Nome: " + funcionario.getNome());
                    System.out.println("Data de Nascimento: " + funcionario.getDataNascimento().format(formatter));
                    System.out.println("Salário: " + String.format(Locale.GERMAN, "%,.2f", funcionario.getSalario()));
                    System.out.println("Função: " + funcionario.getFuncao());
                    System.out.println(); // mostra de forma ordenada
                });

        System.out.println("==Salário total dos funcionários==");
        BigDecimal totalSalario = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println("O salário total dos funcionários é de: " + String.format(Locale.GERMAN, "%,.2f", totalSalario));
        System.out.println();

        System.out.println("==Quantidade de salários mínimos ganho por funcionário==");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        funcionarios.forEach(funcionario -> {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.HALF_UP);
            System.out.println("Nome: " + funcionario.getNome());
            System.out.println("Quantidade de Salários Mínimos: " + quantidadeSalariosMinimos);
            System.out.println();
        });
    }
}