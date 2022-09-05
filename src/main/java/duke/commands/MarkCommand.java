package duke.commands;

import java.io.IOException;
import duke.TaskList;
import duke.DukeException;
import duke.Ui;
import duke.Storage;
import duke.tasks.*;

/**
 * The MarkCommand class encapsulates the execution of a mark command.
 */
public class MarkCommand extends Command{
    private String input;

    public MarkCommand(String input) {
        this.input = input;
    }

    /**
     * Executes the mark command.
     * @param taskList List where a specified task is to be marked as done.
     * @param ui Ui which sends a message to the user after a successful execution or when an error is thrown.
     * @param storage Storage which saves the modified tasklist to the hard disk after successful execution of command.
     * @throws DukeException
     * @throws IOException
     */
    @Override
    public String execute(TaskList taskList, Ui ui, Storage storage) throws DukeException, IOException {
        if (this.input.matches("\\d+")) {
            int markIndex = Integer.parseInt(this.input) - 1;
            if (markIndex < 0 || markIndex >= taskList.length()) {
                throw new DukeException(String.format("There is no task with index %d", markIndex + 1));
            } else {
                Task markTask = taskList.index(markIndex);
                if (markTask.getIsDone()) {
                    throw new DukeException("This task is already marked as done:\n" + markTask.toString());
                } else {
                    markTask.markDone();
                    storage.saveTasks(taskList);
                    return ui.print("Nice! I've marked this task as done:\n" + markTask.toString());
                }
            }
        } else {
            throw new DukeException(this.input + " is not an integer.");
        }
    }
}
