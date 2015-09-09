package common.templates;

import com.github.jknack.handlebars.ValueResolver;
import io.sphere.sdk.models.Base;
import play.i18n.Messages;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class PlayMessagesResolver extends Base implements ValueResolver {

    @Override
    public Object resolve(final Object context, final String name) {
        if (context instanceof Messages) {
            return handleMessages(name, (Messages) context);
        } else if (context instanceof MessageKeyAccumulator) {
            return handleAccumulatedMessage(name, (MessageKeyAccumulator) context);
        } else {
            return UNRESOLVED;
        }
    }

    private Object handleAccumulatedMessage(final String name, final MessageKeyAccumulator helper) {
        final Messages messages = helper.messages;
        final String newKey = helper.key + "." + name;
        return handleMessages(newKey, messages);
    }

    private Object handleMessages(final String name, final Messages messages) {
        if (messages.isDefinedAt(name)) {
            return messages.at(name);
        } else {
            return new MessageKeyAccumulator(name, messages);
        }
    }

    @Override
    public Object resolve(final Object context) {
        return UNRESOLVED;
    }

    @Override
    public Set<Map.Entry<String, Object>> propertySet(final Object context) {
        return Collections.emptySet();
    }

    private static class MessageKeyAccumulator extends Base {
        private final String key;
        private final Messages messages;

        public MessageKeyAccumulator(final String key, final Messages messages) {
            this.key = key;
            this.messages = messages;
        }

        //important!
        //required if the message system cannot find the key it displays for the user an empty string
        @Override
        public final String toString() {
            return "";
        }
    }
}

