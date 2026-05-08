import com.intellij.driver.sdk.ui.components.common.ideFrame
import com.intellij.ide.starter.ide.IdeDownloader
import com.intellij.driver.sdk.waitForIndicators
import com.intellij.driver.sdk.ui.components.UiComponent.Companion.waitFound
import com.intellij.ide.starter.community.IdeByLinkDownloader
import com.intellij.ide.starter.driver.engine.runIdeWithDriver
import com.intellij.ide.starter.junit5.hyphenateWithClass
import com.intellij.ide.starter.models.IdeInfo
import com.intellij.ide.starter.models.TestCase
import com.intellij.ide.starter.project.GitHubProject
import com.intellij.ide.starter.runner.CurrentTestMethod
import com.intellij.ide.starter.runner.Starter
import org.junit.jupiter.api.Test
import kotlin.time.Duration.Companion.minutes
import java.net.URI
import org.kodein.di.bind
import org.kodein.di.singleton
import com.intellij.ide.starter.di.di
import org.kodein.di.DI
import com.intellij.driver.sdk.invokeAction
import com.intellij.driver.sdk.ui.components.elements.button
import com.intellij.driver.sdk.ui.components.elements.dialog
import com.intellij.driver.sdk.ui.components.elements.waitForNoOpenedDialogs
import com.intellij.driver.sdk.ui.xQuery
import java.awt.Toolkit
import java.awt.datatransfer.StringSelection
import java.awt.event.KeyEvent



class ChangelistSettingsTest {
    private fun copyToClipboard(text: String) {
        Toolkit.getDefaultToolkit()
            .systemClipboard
            .setContents(StringSelection(text), null)
    }
    init {
        val baseDi = di

        di = DI {
            extend(baseDi)
            bind<IdeDownloader>(overrides = true) with singleton {
                IdeByLinkDownloader
            }
        }
    }

    @Test
    fun openIdeWithTestProject() {
        val testContext = Starter
            .newContext(
                CurrentTestMethod.hyphenateWithClass(),
                TestCase(
                    IdeInfo(
                        productCode = "IC",
                        platformPrefix = "Idea",
                        executableFileName = "idea",
                        version = "2025.2.6.2",
                        buildNumber = "252.28539.54",
                        downloadURI = URI("https://download.jetbrains.com/idea/ideaIC-2025.2.6.2.exe"),
                        fullName = "IntelliJ IDEA Community"
                    ),
                    GitHubProject.fromGithub(
                        branchName = "master",
                        repoRelativeUrl = "jitpack/gradle-simple.git"
                    )
                )
            )
            //.setupSdk(jdk21.toSdk())
            .prepareProjectCleanImport()

            .runIdeWithDriver().useDriverAndCloseIde {
                waitForIndicators(5.minutes)

                ideFrame {
                    invokeAction("ShowSettings", now = false)

                    dialog(title = "Settings") {
                        Thread.sleep(1500)

                        val searchField = x(xQuery {
                            byType("com.intellij.ui.SearchTextField\$1")
                        }).waitFound()

                        searchField.click()
                        Thread.sleep(500)

                        copyToClipboard("changelists")

                        keyboard {
                            hotKey(KeyEvent.VK_CONTROL, KeyEvent.VK_A)
                            backspace()
                            hotKey(KeyEvent.VK_CONTROL, KeyEvent.VK_V)
                        }

                        Thread.sleep(1500)

                        val checkboxText = "Create changelists automatically"

                        val checkbox = x(xQuery { byAccessibleName(checkboxText) })
                            .waitFound()

                        checkbox.click()

                        Thread.sleep(500)

                        x(xQuery { byAccessibleName(checkboxText) })
                            .waitFound()

                        button("OK").click()
                    }

                    waitForNoOpenedDialogs()
                }
            }
    }
}