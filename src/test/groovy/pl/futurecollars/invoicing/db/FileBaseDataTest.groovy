package pl.futurecollars.invoicing.db

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import pl.futurecollars.invoicing.file.FileService


@SpringBootTest(properties="invoicing-system.database=file")
class FileBaseDataTest extends DatabaseTest {

    @Autowired
    FileBaseData fileBaseData

    @Override
    Database getDatabaseInstance() {
        def fileService = new FileService()
        fileService.clearDatabase()
        return fileBaseData
    }
}