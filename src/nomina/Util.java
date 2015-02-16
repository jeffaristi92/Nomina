/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author yefguaar
 */
public class Util {

    public static boolean escribirArchivo(String rutaArchivo, String contenidoArchivo) {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(rutaArchivo);
            pw = new PrintWriter(fichero);
            pw.println(contenidoArchivo);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Nuevamente aprovechamos el finally para 
                // asegurarnos que se cierra el fichero.
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return true;
    }

    public static ArrayList<String> leerArchivo(String rutaArchivo) {
        ArrayList<String> contenidoArchivo = new ArrayList<>();

        File archivo = null;
        FileReader fr = null;
        BufferedReader br = null;

        try {
            // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            archivo = new File(rutaArchivo);

            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            fr = new FileReader(archivo);
            br = new BufferedReader(fr);

            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
                contenidoArchivo.add(linea);
            }
        } catch (Exception e) {
            contenidoArchivo.clear();
            e.printStackTrace();
        } finally {
            // En el finally cerramos el fichero, para asegurarnos
            // que se cierra tanto si todo va bien como si salta 
            // una excepcion.
            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return contenidoArchivo;
    }

    public static void save(String contenido) {
        String texto = contenido;//variable para comparacion

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("todos los archivos *.HTML", "html", "HTML"));//filtro para ver solo archivos .edu
        int seleccion = fileChooser.showSaveDialog(null);
        try {
            if (seleccion == JFileChooser.APPROVE_OPTION) {//comprueba si ha presionado el boton de aceptar
                File JFC = fileChooser.getSelectedFile();
                String PATH = JFC.getAbsolutePath();//obtenemos el path del archivo a guardar
                PrintWriter printwriter = new PrintWriter(JFC);
                printwriter.print(contenido);//escribe en el archivo todo lo que se encuentre en el JTextArea
                printwriter.close();//cierra el archivo

                //comprobamos si a la hora de guardar obtuvo la extension y si no se la asignamos
                if (!(PATH.endsWith(".html"))) {
                    File temp = new File(PATH + ".html");
                    JFC.renameTo(temp);//renombramos el archivo
                }

                JOptionPane.showMessageDialog(null, "Guardado exitoso!", "Guardado exitoso!", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {//por alguna excepcion salta un mensaje de error
            JOptionPane.showMessageDialog(null, "Error al guardar el archivo!", "Oops! Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private static final String[] UNIDADES = {"", "UN ", "DOS ", "TRES ",
        "CUATRO ", "CINCO ", "SEIS ", "SIETE ", "OCHO ", "NUEVE ", "DIEZ ",
        "ONCE ", "DOCE ", "TRECE ", "CATORCE ", "QUINCE ", "DIECISEIS",
        "DIECISIETE", "DIECIOCHO", "DIECINUEVE", "VEINTE"};

    private static final String[] DECENAS = {"VENTI", "TREINTA ", "CUARENTA ",
        "CINCUENTA ", "SESENTA ", "SETENTA ", "OCHENTA ", "NOVENTA ",
        "CIEN "};

    private static final String[] CENTENAS = {"CIENTO ", "DOSCIENTOS ",
        "TRESCIENTOS ", "CUATROCIENTOS ", "QUINIENTOS ", "SEISCIENTOS ",
        "SETECIENTOS ", "OCHOCIENTOS ", "NOVECIENTOS "};

    /**
     * Convierte a letras un numero de la forma $123,456.32
     *
     * @param number Numero en representacion texto
     * @throws NumberFormatException Si valor del numero no es valido (fuera de
     * rango o )
     * @return Numero en letras
     */
    public static String convertNumberToLetter(String number)
            throws NumberFormatException {
        return convertNumberToLetter(Double.parseDouble(number));
    }

    /**
     * Convierte un numero en representacion numerica a uno en representacion de
     * texto. El numero es valido si esta entre 0 y 999'999.999
     *
     * @param doubleNumber
     * @return Numero convertido a texto
     * @throws NumberFormatException Si el numero esta fuera del rango
     */
    public static String convertNumberToLetter(double doubleNumber)
            throws NumberFormatException {

        StringBuilder converted = new StringBuilder();

        String patternThreeDecimalPoints = "#.###";

        DecimalFormat format = new DecimalFormat(patternThreeDecimalPoints);
        format.setRoundingMode(RoundingMode.DOWN);

        // formateamos el numero, para ajustarlo a el formato de tres puntos
        // decimales
        String formatedDouble = format.format(doubleNumber);
        doubleNumber = Double.parseDouble(formatedDouble);

        // Validamos que sea un numero legal
        if (doubleNumber > 999999999) {
            throw new NumberFormatException(
                    "El numero es mayor de 999'999.999, "
                    + "no es posible convertirlo");
        }

        if (doubleNumber < 0) {
            throw new NumberFormatException("El numero debe ser positivo");
        }

        String splitNumber[] = String.valueOf(doubleNumber).replace('.', '#')
                .split("#");

        // Descompone el trio de millones
        int millon = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                8))
                + String.valueOf(getDigitAt(splitNumber[0], 7))
                + String.valueOf(getDigitAt(splitNumber[0], 6)));
        if (millon == 1) {
            converted.append("UN MILLON ");
        } else if (millon > 1) {
            converted.append(convertNumber(String.valueOf(millon))
                    + "MILLONES ");
        }

        // Descompone el trio de miles
        int miles = Integer.parseInt(String.valueOf(getDigitAt(splitNumber[0],
                5))
                + String.valueOf(getDigitAt(splitNumber[0], 4))
                + String.valueOf(getDigitAt(splitNumber[0], 3)));
        if (miles == 1) {
            converted.append("MIL ");
        } else if (miles > 1) {
            converted.append(convertNumber(String.valueOf(miles)) + "MIL ");
        }

        // Descompone el ultimo trio de unidades
        int cientos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[0], 2))
                + String.valueOf(getDigitAt(splitNumber[0], 1))
                + String.valueOf(getDigitAt(splitNumber[0], 0)));
        if (cientos == 1) {
            converted.append("UN");
        }

        if (millon + miles + cientos == 0) {
            converted.append("CERO");
        }
        if (cientos > 1) {
            converted.append(convertNumber(String.valueOf(cientos)));
        }

        converted.append("PESOS");

        // Descompone los centavos
        int centavos = Integer.parseInt(String.valueOf(getDigitAt(
                splitNumber[1], 2))
                + String.valueOf(getDigitAt(splitNumber[1], 1))
                + String.valueOf(getDigitAt(splitNumber[1], 0)));
        if (centavos == 1) {
            converted.append(" CON UN CENTAVO");
        } else if (centavos > 1) {
            converted.append(" CON " + convertNumber(String.valueOf(centavos))
                    + "CENTAVOS");
        }

        return converted.toString();
    }

    /**
     * Convierte los trios de numeros que componen las unidades, las decenas y
     * las centenas del numero.
     *
     * @param number Numero a convetir en digitos
     * @return Numero convertido en letras
     */
    private static String convertNumber(String number) {

        if (number.length() > 3) {
            throw new NumberFormatException(
                    "La longitud maxima debe ser 3 digitos");
        }

        // Caso especial con el 100
        if (number.equals("100")) {
            return "CIEN";
        }

        StringBuilder output = new StringBuilder();
        if (getDigitAt(number, 2) != 0) {
            output.append(CENTENAS[getDigitAt(number, 2) - 1]);
        }

        int k = Integer.parseInt(String.valueOf(getDigitAt(number, 1))
                + String.valueOf(getDigitAt(number, 0)));

        if (k <= 20) {
            output.append(UNIDADES[k]);
        } else if (k > 30 && getDigitAt(number, 0) != 0) {
            output.append(DECENAS[getDigitAt(number, 1) - 2] + "Y "
                    + UNIDADES[getDigitAt(number, 0)]);
        } else {
            output.append(DECENAS[getDigitAt(number, 1) - 2]
                    + UNIDADES[getDigitAt(number, 0)]);
        }

        return output.toString();
    }

    /**
     * Retorna el digito numerico en la posicion indicada de derecha a izquierda
     *
     * @param origin Cadena en la cual se busca el digito
     * @param position Posicion de derecha a izquierda a retornar
     * @return Digito ubicado en la posicion indicada
     */
    private static int getDigitAt(String origin, int position) {
        if (origin.length() > position && position >= 0) {
            return origin.charAt(origin.length() - position - 1) - 48;
        }
        return 0;
    }

    public static boolean validarCampoNumerico(KeyEvent evt, boolean decimal, String cadena) {
        char key = evt.getKeyChar();
        if (Character.isDigit(key) || (key == '.')) {
            if (decimal) {
                if (key == '.') {
                    if (cadena.contains(".")) {
                        return false;
                    }
                }
            } else {
                if (key == '.') {
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

}
