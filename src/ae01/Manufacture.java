package ae01;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Manufacture {

    private String type;
    private int quantity;
    private String filename;
    private List<String> piecesList;
    private Queue<Integer> machines;

    public Manufacture(String type, int quantity, String filename) {
        this.type = type;
        this.quantity = quantity;
        this.filename = filename;
        this.piecesList = new ArrayList<>();
        this.machines = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            machines.offer(i);
        }
    }

    public void startManufacturing() {
        for (int i = 0; i < quantity; i++) {
            int machineNumber = machines.poll();
            PieceManufacturingThread thread = new PieceManufacturingThread(type, machineNumber);
            thread.start();
        }
    }

    private synchronized void addPieceToList(String piece) {
        piecesList.add(piece);
        System.out.println(piece);
        machines.offer(piece.hashCode() % 8); // Simulación de liberar la máquina
    }

    private void writeListToFile() {
        String fileNameWithTimestamp;
        if (filename.isEmpty()) {
            // Si no se proporciona un nombre de archivo, usa un timestamp
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            fileNameWithTimestamp = "LOG_" + timestamp + ".txt";
        } else {
            // Si se proporciona un nombre de archivo, úsalo y asegúrate de que tenga la extensión .txt
            if (filename.toLowerCase().endsWith(".txt")) {
                fileNameWithTimestamp = filename;
            } else {
                fileNameWithTimestamp = filename + ".txt";
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileNameWithTimestamp))) {
            for (String piece : piecesList) {
                writer.write(piece);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private class PieceManufacturingThread extends Thread {
        private String type;
        private int machineNumber;

        public PieceManufacturingThread(String type, int machineNumber) {
            this.type = type;
            this.machineNumber = machineNumber;
        }

        @Override
        public void run() {
            // Simulación del tiempo de fabricación
            int timeManufacture = getTimeManufacture(type);
            procesFabricacio(timeManufacture);

            // Construir el nombre de la pieza y añadirla a la lista
            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String piece = type + "_" + timestamp;
            addPieceToList(piece);

            // Si todas las piezas han sido fabricadas, escribir la lista en el archivo
            if (piecesList.size() == quantity) {
                writeListToFile();
            }
        }

        private int getTimeManufacture(String type) {
            switch (type) {
                case "I":
                    return 1000;
                case "O":
                    return 2000;
                case "T":
                    return 3000;
                case "J":
                case "L":
                    return 4000;
                case "S":
                case "Z":
                    return 5000;
                default:
                    return 0;
            }
        }

        public void procesFabricacio(int tempsFabricacio) {
            long tempsInici = System.currentTimeMillis();
            long tempsFinal = tempsInici + tempsFabricacio; // Temps de fabricació en milisegons
            int iteracions = 0;
            while (System.currentTimeMillis() < tempsFinal) {
                // Realitzar iteracions addicionals per a consumir processador (simula ocupacio de maquina)
                iteracions++;
            }
        }
    }
}
