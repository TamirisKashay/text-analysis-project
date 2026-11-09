import benchmark.ExperimentRunner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Запуск экспериментов и анализа текста...\n");
        ExperimentRunner.runAllExperiments();
        System.out.println("\nЭксперименты завершены успешно!");
    }
}