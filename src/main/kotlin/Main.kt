// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.awt.ComposePanel
import java.awt.*
import java.awt.event.ActionListener
import javax.swing.JDialog
import javax.swing.SwingUtilities
import javax.swing.WindowConstants
import kotlin.system.exitProcess

// Thanks to https://stackoverflow.com/questions/2054347/show-jframe-but-not-show-title-bar-on-task-bar

fun main() = SwingUtilities.invokeLater {
    val jFrameDialog = JDialog() // Default behaviour of JDialog in Windows - not show app in task bark
    jFrameDialog.defaultCloseOperation = WindowConstants.HIDE_ON_CLOSE //todo you can't use EXIT_ON_CLOSE
    jFrameDialog.title = "SwingComposeDialog"
    val composePanel = ComposePanel()
    jFrameDialog.contentPane.add(composePanel, BorderLayout.CENTER)
    composePanel.setContent {
        Column {
            Text("This is ComposePanel inside JDialog")
            Text("Main Goal is no hide app in Windows Task Bar")

            TextButton("close application") {
                System.exit(0)
            }

            TextButton("Configure tray icon") {
                val image = Toolkit.getDefaultToolkit().getImage("/media/faisal/DukeImg/Duke256.png")
                val popup = PopupMenu()
                val item1 = MenuItem("Exit")
                item1.addActionListener(ActionListener {
                    println("Exiting....")
                    System.exit(0)
                })
                popup.add(item1)
                val item2 = MenuItem("Hello from tray")
                item2.addActionListener {
                    println("Hello from system tray")
                }
                popup.add(item2)
                val trayIcon = TrayIcon(image, "SystemTray Demo", popup)
                trayIcon.setImageAutoSize(true)
                SystemTray.getSystemTray().add(trayIcon)
            }
        }
    }
    jFrameDialog.setSize(800, 600)
    jFrameDialog.isVisible = true
}

@Composable
fun TextButton(text: String, action: () -> Unit) {
    Button(onClick = action) {
        Text(text)
    }
}

//fun main() = application {
//    Dialog(onCloseRequest = ::exitApplication) {
//        Column {
//            Text("Text inside Compose")
//        }
//    }
//}
