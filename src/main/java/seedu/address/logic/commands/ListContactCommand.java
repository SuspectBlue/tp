package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CUSTOMER;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_SERVICE;

import seedu.address.model.Model;

/**
 * Lists all contacts in the address book to the user.
 */
public class ListContactCommand extends Command {

    public static final String COMMAND_WORD = "listContact";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Lists all the contacts or contacts that have a specific tag.\n"
        + "Parameters: TAGNAME (optional)\n"
        + "Examples: " + COMMAND_WORD + " OR " + COMMAND_WORD + " customer" + " OR " + COMMAND_WORD + " service";

    public static final String MESSAGE_SUCCESS = "Listed all %scontacts.";

    private final String tagName;

    /**
     * Creates a ListContactCommand to list contacts based on the specified tag.
     * If no tag is specified, all contacts will be listed.
     *
     * @param tagname The tag name to filter contacts by. Can be empty, "customer", or "service".
     * @throws AssertionError if tagname is null.
     */
    public ListContactCommand(String tagname) {
        assert tagname != null : "Tag name cannot be null";
        this.tagName = tagname;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        if (tagName.equals("customer")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_CUSTOMER);
            assert model.getFilteredPersonList().stream().allMatch(contact -> contact.isCustomer());
        } else if (tagName.equals("service")) {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_SERVICE);
            assert model.getFilteredPersonList().stream().allMatch(contact -> contact.isService());
        } else {
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            assert model.getFilteredPersonList().size() == model.getAddressBook().getPersonList().size();
        }

        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format("There are currently no %scontacts in the addressbook.", (
                tagName.equals("") ? "" : tagName + " ")));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, (tagName.equals("") ? "" : tagName + " ")));

    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ListContactCommand)) {
            return false;
        }

        ListContactCommand otherListContactCommand = (ListContactCommand) other;
        return tagName.equals(otherListContactCommand.tagName);
    }
}
