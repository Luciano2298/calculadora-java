import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CalculadoraApp extends JFrame implements ActionListener {
    // Componentes de la calculadora
    private JTextField pantalla;
    private String operador;
    private double primerOperando;
    private boolean operadorPresionado;

    // Constructor para configurar la interfaz
    public CalculadoraApp() {
        // Configurar la ventana
        setTitle("Calculadora");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Crear el campo de visualización
        pantalla = new JTextField();
        pantalla.setEditable(false);
        pantalla.setFont(new Font("Arial", Font.PLAIN, 24));
        add(pantalla, BorderLayout.NORTH);

        // Crear botones para dígitos y operaciones
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(5, 4));

        String[] etiquetasBotones = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "C" // Botón para limpiar
        };

        for (String etiqueta : etiquetasBotones) {
            JButton boton = new JButton(etiqueta);
            boton.setFont(new Font("Arial", Font.PLAIN, 24));
            boton.addActionListener(this);
            panelBotones.add(boton);
        }

        add(panelBotones, BorderLayout.CENTER);

        // Inicializar variables
        operador = "";
        primerOperando = 0;
        operadorPresionado = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        try {
            // Manejar la entrada numérica y el punto decimal
            if (comando.matches("[0-9]") || comando.equals(".")) {
                if (operadorPresionado) {
                    // Reemplazar el resultado en la pantalla por el nuevo número
                    pantalla.setText(comando);
                    operadorPresionado = false;
                } else {
                    pantalla.setText(pantalla.getText() + comando);
                }
            }
            // Manejar el botón de limpiar ("C")
            else if (comando.equals("C")) {
                // Limpiar solo el contenido de la pantalla
                pantalla.setText("");
                operador = ""; // Reiniciar el operador para continuar
                primerOperando = 0; // Reiniciar el primer operando
                operadorPresionado = false; // Reiniciar el estado del operador
            }
            // Manejar el botón de igual
            else if (comando.equals("=")) {
                double segundoOperando = Double.parseDouble(pantalla.getText());
                if (operador.equals("/") && segundoOperando == 0) {
                    throw new ArithmeticException("No se puede dividir entre cero.");
                }

                double resultado = calcular(primerOperando, segundoOperando, operador);
                pantalla.setText(String.valueOf(resultado));
                operador = "";
                operadorPresionado = true; // Indicar que se ha dado un resultado
            }
            // Manejar botones de operadores (+, -, *, /)
            else {
                primerOperando = Double.parseDouble(pantalla.getText());
                operador = comando;
                operadorPresionado = true;

                // Mostrar la operación que se va a realizar en la pantalla
                pantalla.setText("Operación: " + operador);
            }
        } catch (ArithmeticException ae) {
            // Manejar división por cero
            pantalla.setText("Error: " + ae.getMessage());
        } catch (NumberFormatException nfe) {
            // Manejar formato de número inválido
            pantalla.setText("Error: Entrada no válida. Por favor, ingresa un número.");
        } catch (Exception ex) {
            // Manejar cualquier otra excepción inesperada
            pantalla.setText("Error: " + ex.getMessage());
        }
    }

    // Realizar el cálculo basado en el operador
    private double calcular(double a, double b, String operador) {
        switch (operador) {
            case "+": return a + b;
            case "-": return a - b;
            case "*": return a * b;
            case "/": return a / b;
            default: return 0;
        }
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana de la calculadora
        CalculadoraApp calculadora = new CalculadoraApp();
        calculadora.setVisible(true);
    }
}
