package btc.renaud.dialogmenu.util

import io.papermc.paper.dialog.DialogResponseView
import io.papermc.paper.registry.data.dialog.action.DialogAction
import net.kyori.adventure.text.event.ClickCallback
import org.bukkit.entity.Player
import kotlin.math.roundToInt

private val macroRegex = Regex("\\$\\(([^\\)]+)\\)")

fun commandAction(
    command: String?,
    booleanValues: Map<String, Pair<String, String>>,
    onDone: (Player) -> Unit
): DialogAction {
    val trimmed = command?.trim().orEmpty()
    return DialogAction.customClick({ response: DialogResponseView, audience ->
        val player = audience as? Player ?: return@customClick
        if (trimmed.isNotBlank()) {
            var cmd = trimmed
            macroRegex.findAll(trimmed).forEach { match ->
                val key = match.groupValues[1]
                val mapping = booleanValues[key]
                val value = response.getText(key)
                    ?: if (mapping != null) {
                        response.getBoolean(key)?.let { bool ->
                            if (bool) mapping.first else mapping.second
                        } ?: response.getFloat(key)?.let { f ->
                            if (f.roundToInt() == 1) mapping.first else mapping.second
                        }
                    } else {
                        response.getFloat(key)?.roundToInt()?.toString()
                            ?: response.getBoolean(key)?.toString()
                    }
                    ?: ""
                cmd = cmd.replace(match.value, value)
            }
            cmd = cmd.trim()
            if (cmd.isNotBlank()) {
                runCatching { player.performCommand(cmd) }
            }
        }
        onDone(player)
    }, ClickCallback.Options.builder().uses(1).build())
}

