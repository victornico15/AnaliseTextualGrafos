import cc.mallet.pipe.*;
import cc.mallet.types.Instance;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class File2CharSequence extends Pipe {
    public Instance pipe(Instance carrier) {
        File file = (File) carrier.getData();
        String content;
        try {
            content = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        carrier.setData(content);
        return carrier;
    }
}
