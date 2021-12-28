package de.jensklingenberg.htmltocfw.ideaplugin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.text.input.TextFieldValue
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.CommonDataKeys
import com.intellij.openapi.command.WriteCommandAction
import com.intellij.openapi.project.DumbAwareAction
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.DialogWrapper
import de.jensklingenberg.htmltocfw.converter.htmlToCompose
import de.jensklingenberg.htmltocfw.ideaplugin.theme.WidgetTheme
import javax.swing.JComponent



class ConvertHtmlAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val editor = e.getRequiredData(CommonDataKeys.EDITOR)
        val project = e.getRequiredData(CommonDataKeys.PROJECT)

        // Work off of the primary caret to get the selection info
        val primaryCaret = editor.caretModel.primaryCaret
        val start = primaryCaret.selectionStart
        val end = primaryCaret.selectionEnd


        DemoDialog(project = e.project, onGenerate = {
            val compo = htmlToCompose(it)

            WriteCommandAction.runWriteCommandAction(project) {
                editor.document.replaceString(start, end, compo)
            }
        }).show()


    }

    class DemoDialog(project: Project?, val onGenerate: (String) -> Unit) : DialogWrapper(project) {
        init {
            title = "HTML to Compose for Web"
            init()
        }

        override fun createCenterPanel(): JComponent {
            return ComposePanel().apply {

                setBounds(0, 0, 800, 600)
                setContent {
                    WidgetTheme(darkTheme = true) {
                        Surface(modifier = Modifier.fillMaxSize()) {
                            Column {
                                val textState = remember { mutableStateOf(TextFieldValue()) }

                                Button(onClick = { onGenerate(textState.value.text) }) {
                                    Text("Generate")
                                }
                                Text("Paste your HTML into the textfield and press generate")
                                TextField(
                                    value = textState.value,
                                    onValueChange = { textState.value = it }
                                )

                            }
                        }
                    }
                }
            }
        }
    }
}
