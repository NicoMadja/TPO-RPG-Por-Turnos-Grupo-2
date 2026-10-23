package app;
 // o ponlo en el paquete que prefieras

import controlador.ControladorMapa;
import modelo.batalla.Batalla;
import modelo.campamento.Campamento;
import modelo.mundo.mapa.Mapa;
import modelo.mundo.mapa.prefabs.Mapa1;
import modelo.party.Party;
import modelo.mundo.EscenarioDeNodos;
import modelo.tienda.Tienda;
import vista.pantallas.PantallaMapa;

import javax.swing.*;

public class TestMapa {
    public static void main(String[] args) {
        // SwingUtilities asegura que la interfaz gráfica se ejecute en el hilo correcto
        SwingUtilities.invokeLater(() -> {

            // 1. Crear una ventana de prueba
            JFrame ventana = new JFrame("Test Visual del Mapa");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            ventana.setSize(800, 600);
            ventana.setLocationRelativeTo(null); // Centrar en pantalla

            // 2. Crear el Modelo (Party y Mapa)
            // NOTA: Ajusta el new Party() según cómo sea el constructor de tu clase Party
            Party partyDePrueba = new Party();

            Mapa1 prefab = new Mapa1();
            Mapa mapaGenerado = prefab.crear(partyDePrueba);

            // 3. Crear la Vista (El JPanel)
            PantallaMapa vistaMapa = new PantallaMapa();
            ventana.add(vistaMapa); // Agregamos el panel a la ventana

            // 4. Crear un "EscenarioDeNodos" falso (Mock) para atrapar los clics
            // Esto evita que el juego crashee al hacer clic en un nodo, ya que solo queremos probar el mapa.
            EscenarioDeNodos escenarioPrueba = new EscenarioDeNodos() {
                @Override
                public void alEntrarEnBatalla(Batalla batalla) {
                    System.out.println("¡Has entrado a un nodo de Batalla!");
                }

                @Override
                public void alEntrarEnTienda(Tienda tienda) {
                    System.out.println("¡Has entrado a la Tienda!");
                }

                @Override
                public void alEntrarEnCampamento(Campamento campamento) {
                    System.out.println("¡Estás descansando en el Campamento!");

            };
                // ¡ATENCIÓN! Aquí el IDE te pedirá que implementes los métodos de tu interfaz.
                // Simplemente dales a "Implement methods" y ponles un System.out.println adentro.
                // Ejemplo:
                // @Override
                // public void iniciarBatalla(Batalla batalla) {
                //     System.out.println("¡Has entrado a un nodo de Batalla!");
                // }
            };

            // 5. Conectar todo usando el Controlador
            ControladorMapa controlador = new ControladorMapa(mapaGenerado, prefab.getCoordenadasVisuales(), vistaMapa, escenarioPrueba);

            // 6. Mostrar la ventana
            ventana.setVisible(true);
        });
    }
}
