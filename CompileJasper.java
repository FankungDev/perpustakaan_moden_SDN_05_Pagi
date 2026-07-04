import java.io.File;
import net.sf.jasperreports.engine.JasperCompileManager;

public class CompileJasper {
    public static void main(String[] args) {
        try {
            File reportDir = new File("src/Reports");
            if (!reportDir.exists() || !reportDir.isDirectory()) {
                System.out.println("Direktori src/Reports tidak ditemukan!");
                return;
            }
            
            File[] files = reportDir.listFiles((dir, name) -> name.endsWith(".jrxml"));
            if (files == null || files.length == 0) {
                System.out.println("Tidak ada file .jrxml di src/Reports");
                return;
            }
            
            for (File file : files) {
                String jrxmlPath = file.getPath();
                String jasperPath = jrxmlPath.substring(0, jrxmlPath.lastIndexOf('.')) + ".jasper";
                System.out.println("Meng-compile (src): " + jrxmlPath + " -> " + jasperPath);
                JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);
                
                // Juga compile ke build/classes/Reports agar langsung aktif tanpa Clean & Build
                File buildDir = new File("build/classes/Reports");
                if (buildDir.exists() && buildDir.isDirectory()) {
                    String buildJasperPath = new File(buildDir, file.getName().substring(0, file.getName().lastIndexOf('.')) + ".jasper").getPath();
                    System.out.println("Meng-compile (build): " + jrxmlPath + " -> " + buildJasperPath);
                    JasperCompileManager.compileReportToFile(jrxmlPath, buildJasperPath);
                }
                System.out.println("Compile berhasil untuk: " + file.getName());
            }
            System.out.println("Semua file Jasper berhasil di-compile!");
        } catch (Exception e) {
            System.out.println("Error saat compile: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
