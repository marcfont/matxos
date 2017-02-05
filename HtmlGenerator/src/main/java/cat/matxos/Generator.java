package cat.matxos;


import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;

public class Generator {

    public static void main (String[] args) {

        try {
            StringBuffer content = new StringBuffer(10000);

            String header = IOUtils.toString(Generator.class.getClassLoader().getResourceAsStream("header"));
            String row =  IOUtils.toString(Generator.class.getClassLoader().getResourceAsStream("row"));
            String timesSrc =  IOUtils.toString(Generator.class.getClassLoader().getResourceAsStream("volcat.csv"));
            String[] times = timesSrc.split("\n");

            content.append(header);
            int i = 0; //css id
            for (String t : times) {
                //  0        1       2          3        4            5         6          7         8            9        10       11       12   13
                //Dorsal,Les Valls,Bellmunt,Salgueda,St Bartomeu,Puigsacalm,Prat Vola 1,Cabrera,Prat Vola 2,Collsaplana,Sant Pere,Arribada,Ruta,NOM,Abandonat,Comentaris,Alti
                String[] data = t.split(",");


                // dorsal, nom, ruta, temps
                // expanded: temps de: Les Valls Bellmunt Salgueda Bartomeu Puigsacalm Vola1 Cabrera Vola2 Collsaplana StPere

                String rowContent = String.format(row, i, f(data,0), f(data,13), f(data,12), f(data,11), i, f(data,1), f(data,2), f(data,3), f(data,4), f(data,5), f(data,6), f(data,7), f(data,8), f(data,9), f(data,10) );
                content.append(rowContent);
                i++;
            }
            String footer =  IOUtils.toString(Generator.class.getClassLoader().getResourceAsStream("foot"));
            content.append(footer);

            File file = new File("test.html");
            FileUtils.writeStringToFile(file, content.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String f(String[] data, int pos) {
        if (data.length<=pos) {
            return  "-";
        }
        String raw = data[pos];
        if (raw == null || raw.isEmpty()){
            return "-";
        }
        return raw;
    }


}
