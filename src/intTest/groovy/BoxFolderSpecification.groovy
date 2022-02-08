import com.box.sdk.BoxAPIConnection
import com.box.sdk.BoxFolder
import com.box.sdk.BoxItem
import com.box.sdk.BoxUser
import com.box.sdk.TestConfig
import spock.lang.Specification

import static com.box.sdk.UniqueTestFolder.getUniqueFolder
import static org.hamcrest.Matchers.equalTo
import static org.hamcrest.Matchers.hasItem
import static org.spockframework.util.Matchers.not

class BoxFolderSpecification extends Specification {
//    def setupSpec() {
//        setupUniqeFolder()
//    }
//
//    def cleanupSpec() {
//        removeUniqueFolder()
//    }

    def "maximum of two numbers"(int a, int b, int c) {
        expect:
        Math.max(a, b) == c

        where:
        a | b | c
        1 | 3 | 3
        7 | 4 | 7
        0 | 0 | 0
    }

    def "creating and deleting folder succeeds"() {
        given:
        BoxAPIConnection api = new BoxAPIConnection(TestConfig.getAccessToken())
        def rootFolder = BoxFolder.getRootFolder(api)

        when:
        BoxFolder childFolder =
                rootFolder.createFolder("[creatingAndDeletingFolderSucceeds] Ĥȅľľő Ƒŕőďő").getResource()

        then:
        rootFolder hasItem(hasProperty("ID", equalTo(childFolder.getID())))

        when:
        childFolder.delete(false)

        then:
        rootFolder not(hasItem(hasProperty("ID", equalTo(childFolder.getID()))))
    }

    def "gets info"() {
        given:
        def api = new BoxAPIConnection(TestConfig.getAccessToken())
        def currentUser = BoxUser.getCurrentUser(api)
        def expectedName = "[getFolderInfoReturnsCorrectInfo] Child Folder"
        def expectedCreatedByID = currentUser.getID()

        def rootFolder = getUniqueFolder(api)
        def expectedParentFolderID = rootFolder.getID()
        def expectedParentFolderName = rootFolder.getInfo().getName()

        when:
        def childFolder = rootFolder.createFolder(expectedName).getResource()
        def info = childFolder.getInfo(BoxItem.ALL_FIELDS)
        info.getPathCollection().forEach(p -> println("p ${p.getID()}"))

        then:
        with(info) {
            getName() == expectedName
            getCreatedBy().getID() == expectedCreatedByID
            getParent().getID() == expectedParentFolderID
            getParent().getName() == expectedParentFolderName
            getPermissions() == EnumSet.allOf(BoxFolder.Permission.class)
            getItemStatus() == "active"
        }

        cleanup:
        deleteFolder(childFolder)
    }

    private def deleteFolder(BoxFolder folder) {
        if (folder != null) {
            folder.delete(true)
        }
    }
}