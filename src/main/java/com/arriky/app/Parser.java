package com.arriky.app;

import com.arriky.exception.ArrikyRuntimeException;
import com.arriky.exception.ErrorMessage;
import com.arriky.exception.IncorrectArgumentAmountException;
import com.arriky.task.TaskList;

public class Parser {
    public static boolean parseCommand(String rawCommand, TaskList taskList) throws ArrikyRuntimeException {
        String[] arguments = rawCommand.split(" ");

        switch (arguments[0]) {
        case "bye":
            try {
                if (arguments.length != 1) {
                    throw new IncorrectArgumentAmountException();
                }
                UI.endSession();
                return false;
            } catch (IncorrectArgumentAmountException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INCORRECT_ARGUMENT_AMOUNT_0);
            }

        case "list":
            try {
                if (arguments.length != 1) {
                    throw new IncorrectArgumentAmountException();
                }
                UI.listAllTasks(taskList);
            } catch (IncorrectArgumentAmountException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INCORRECT_ARGUMENT_AMOUNT_0);
            }
            break;

        case "mark":
            try {
                if (arguments.length != 2) {
                    throw new IncorrectArgumentAmountException();
                }
                taskList.markDone(Integer.parseInt(arguments[1]) - 1);
                UI.printMarkDoneAcknowledgement(taskList,Integer.parseInt(arguments[1]) - 1);
            } catch (NumberFormatException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INVALID_ID);
            } catch (IndexOutOfBoundsException e) {
                throw new ArrikyRuntimeException(ErrorMessage.ID_NOT_EXIST);
            } catch (IncorrectArgumentAmountException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INCORRECT_ARGUMENT_AMOUNT_1);
            }
            break;

        case "unmark":
            try {
                if (arguments.length != 2) {
                    throw new IncorrectArgumentAmountException();
                }
                taskList.unmarkDone(Integer.parseInt(arguments[1]) - 1);
                UI.printUnmarkDoneAcknowledgement(taskList,Integer.parseInt(arguments[1]) - 1);
            } catch (NumberFormatException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INVALID_ID);
            } catch (IndexOutOfBoundsException e) {
                throw new ArrikyRuntimeException(ErrorMessage.ID_NOT_EXIST);
            } catch (IncorrectArgumentAmountException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INCORRECT_ARGUMENT_AMOUNT_1);
            }
            break;

        case "todo":
            String taskName = rawCommand.substring(5);
            taskList.addToDo(taskName, false);
            UI.printInsertionAcknowledgement(taskList);
            break;

        case "deadline":
            try {
                String[] segments = rawCommand.split(" /by ");
                taskList.addDeadline(segments[0].substring(9), segments[1], false);
                UI.printInsertionAcknowledgement(taskList);
            } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INVALID_DEADLINE_FORMAT);
            }
            break;

        case "event":
            try {
                String[] segments = rawCommand.split(" /");
                taskList.addEvent(segments[0].substring(6), segments[1].substring(5), segments[2].substring(3), false);
                UI.printInsertionAcknowledgement(taskList);
            } catch (StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INVALID_EVENT_FORMAT);
            }
            break;

        case "delete":
            try {
                if (arguments.length != 2) {
                    throw new IncorrectArgumentAmountException();
                }
                String summary = taskList.getSummaryByIndex(Integer.parseInt(arguments[1]) - 1);
                taskList.delete(Integer.parseInt(arguments[1]) - 1);
                UI.printDeletionAcknowledgement(taskList, summary);
            } catch (NumberFormatException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INVALID_ID);
            } catch (IndexOutOfBoundsException e) {
                throw new ArrikyRuntimeException(ErrorMessage.ID_NOT_EXIST);
            } catch (IncorrectArgumentAmountException e) {
                throw new ArrikyRuntimeException(ErrorMessage.INCORRECT_ARGUMENT_AMOUNT_1);
            }
            break;

        default:
            throw new ArrikyRuntimeException(ErrorMessage.INVALID_COMMAND);
        }

        return true;
    }
}