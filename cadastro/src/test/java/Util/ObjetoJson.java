package Util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public final class ObjetoJson {

    private final static ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());

    public static <T> T getMockObject(String mockFolder, String fileName, Class<T> targetClazz) {
        return doGetMockObject(mockFolder, fileName, targetClazz, null);
    }

    public static <T, E extends Collection<?>> T doGetMockObject(String mockFolder, String fileName, Class<T> targetClazz, @Nullable Class<E> collectionClazz) {
        String filePath = mockFolder + "/" + fileName;

        try (InputStream is = ObjetoJson.class.getResourceAsStream(filePath)) {
            if (collectionClazz != null) {
                CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(collectionClazz, targetClazz);
                return mapper.readValue(is, collectionType);
            } else {
                return mapper.readValue(is, targetClazz);
            }
        } catch (IOException e){
            e.printStackTrace();
            throw new RuntimeException("Um erro ocorreu ao carregar o JSON de teste: " + filePath);
        }
    }

}
