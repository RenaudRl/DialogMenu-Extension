# Typewriter-DialogMenuExtension

Provides Typewriter actions that use Paper's Dialog API to display in-game menus.

Commands run through these dialogs now support both static commands and command templates using the `$(var)` syntax. When no variables are present, the command executes directly.

## Available Entries

- `paper_notice_dialog` – shows a notice dialog with a single button.
- `paper_confirmation_dialog` – displays a confirmation dialog with "Yes" and "No" buttons and optional commands.
- `paper_multi_dialog` – opens a dialog with multiple buttons, supports an optional exit button and configurable column count.
- `paper_text_input_dialog` – requests a text input with configurable field settings and runs a command with the provided value.

