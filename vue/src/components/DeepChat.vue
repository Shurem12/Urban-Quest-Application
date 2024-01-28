<template>
  <div class="deep-chat-container">
    <deep-chat
      :demo="demo"
      :textInput="textInput"
      :initialMessages="initialMessages"
    />
  </div>
</template>
  
  <script>
import { DeepChat } from "deep-chat";

export default {
  name: "DeepChatComponent",
  components: {
    DeepChat,
  },
  props: {
    demo: {
      type: Boolean,
      default: true,
    },
    textInput: {
      type: Object,
      default: () => ({ placeholder: { text: "Type your message..." } }),
    },
    initialMessages: {
      type: Array,
      default: () => [],
    },
  },
};
</script>
  