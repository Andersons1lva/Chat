import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.anderson.chatui.data.Author
import dev.anderson.chatui.data.Message
import kotlin.time.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


@Composable
@Preview(widthDp = 1000)
fun ChatApp() {
    val chats = listOf(
        Author(
            "Maria Silva",
            "Olá, como você está?",
            Instant.parse("2026-02-21T20:57:12.844116200Z")
        ),
        Author("João Santos", "Tudo bem?", Instant.parse("2026-02-21T20:57:12.844116200Z"))
    )
    var selectedChat by remember { mutableStateOf(chats[0]) }
    val messages = listOf(
        Message(
            1,
            "Olá, tudo bem?",
            isPropria = false,
            timestamp = Instant.parse("2026-02-21T20:57:12.844116200Z"),
            contents = "",
            replyTo = null
        ),
        Message(
            2,
            "Tudo ótimo! E você?",
            isPropria = true,
            timestamp = Instant.parse("2026-02-21T20:57:12.844116200Z"),
            contents = "",
            replyTo = null
        ),
        Message(
            3,
            "Estou bem também.",
            isPropria = false,
            timestamp = Instant.parse("2026-02-21T20:57:12.844116200Z"),
            contents = "",
            replyTo = null
        )
    )

    MaterialTheme {
        Scaffold { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {

                // Conteúdo principal ocupa a tela inteira
                Row(modifier = Modifier.fillMaxSize()) {

                    ChatList(
                        chats = chats,
                        selectedChat = selectedChat,
                        onSelect = { selectedChat = it },
                        modifier = Modifier
                            .weight(0.25f)
                            .fillMaxHeight()
                    )

                    ChatMessages(
                        messages = messages,
                        modifier = Modifier
                            .weight(0.5f)
                            .fillMaxHeight()
                            .padding(bottom = 72.dp) // espaço pro input não cobrir as msgs
                    )

                    Column(
                        modifier = Modifier
                            .weight(0.25f)
                            .fillMaxHeight()
                            .padding(bottom = 72.dp) // espaço pro input não cobrir o ChatDetails
                    ) {
                        ChatDetails(
                            chat = selectedChat,
                            chats = chats,
                            selectedChat = selectedChat,
                            onSelect = { selectedChat = it },
                            modifier = Modifier.fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.weight(1f))
                    }
                }

                // ChatInput flutuando no rodapé, sem roubar altura
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(0.25f)) // começa após ChatList

                    ChatInput(
                        modifier = Modifier.weight(0.75f)
                    )
                }
            }
        }


    }
}


@Composable
fun ChatList(
    chats: List<Author>,
    selectedChat: Author,
    onSelect: (Author) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.width(300.dp).fillMaxHeight()) {
        LazyColumn {
            items(chats) { chat ->
                ChatListItem(
                    chat = chat,
                    isSelected = chat == selectedChat,
                    onClick = { onSelect(chat) }
                )
            }
        }
    }
}

@Composable
fun ChatListItem(chat: Author, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFF007AFF).copy(alpha = 0.1f) else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(8.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ImageAvatar(name = chat.name)
        Spacer(Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(chat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(chat.lastMessage, fontSize = 14.sp, color = Color.Gray)
        }
        Text(chat.time.toHourMinute(), fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun ChatItemRecebidos(chat: Author, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFF00FF7F).copy(alpha = 0.1f) else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(chat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(chat.lastMessage, fontSize = 14.sp, color = Color.Gray)
        }
        Text(chat.time.toHourMinute(), fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun ChatItemNaoRecebidos(chat: Author, isSelected: Boolean, onClick: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(if (isSelected) Color(0xFFFF4D4D).copy(alpha = 0.1f) else Color.Transparent)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(chat.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Text(chat.lastMessage, fontSize = 14.sp, color = Color.Gray)
        }
        Text(chat.time.toHourMinute(), fontSize = 12.sp, color = Color.Gray)
    }
}

@Composable
fun ChatMessages(messages: List<Message>, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            reverseLayout = true,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(messages.reversed()) { message ->
                MessageBubble(message)
            }
        }
    }
}

@Composable
fun MessageBubble(message: Message) {
    Row(
        horizontalArrangement = if (message.isPropria) Arrangement.End else Arrangement.Start,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (!message.isPropria) {
            ImageAvatar("Maria")
            Spacer(Modifier.width(8.dp))
        }
        Card(
            shape = if (message.isPropria) RoundedCornerShape(20.dp, 4.dp, 20.dp, 20.dp)
            else RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (message.isPropria) Color(0xFF007AFF) else Color.LightGray
            )
        ) {
            Text(
                text = message.author,
                modifier = Modifier.padding(12.dp),
                color = if (message.isPropria) Color.White else Color.Black,
                fontSize = 16.sp
            )
        }
        if (message.isPropria) {
            Text(
                message.timestamp.toHourMinute(),
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 12.sp,
                color = Color.Gray
            )
        }
    }
}


fun Instant.toHourMinute(): String {
    val local = this.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${local.hour.toString().padStart(2, '0')}:${local.minute.toString().padStart(2, '0')}"
}


@Composable
fun ImageAvatar(name: String, modifier: Modifier = Modifier) {
    val color = Color(0xFF007AFF) // Cor fixa ou hash-based
    Box(
        modifier = modifier.size(48.dp).clip(CircleShape).background(color)
    ) {
        Text(
            text = name.take(2).uppercase(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun ChatInput(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        var textState by remember { mutableStateOf("") }
        val isChatEmpty = textState.isBlank()

        OutlinedTextField(
            value = textState,
            onValueChange = { texto -> textState = texto },
            modifier = Modifier.weight(1f),
            shape = RoundedCornerShape(24.dp),
            placeholder = { Text("Digite uma mensagem") },
            trailingIcon = {
                IconButton(
                    onClick = {
                            println("Mensagem enviada: $textState")
                            textState = ""
                    },
                    enabled = !isChatEmpty
                ) {
                    Icon(Icons.Default.Send, contentDescription = "Enviar")
                }
            }
        )
    }
}

@Composable
fun ChatDetails(
    chat: Author,
    chats: List<Author>,
    selectedChat: Author,
    onSelect: (Author) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .wrapContentHeight(), // 👈 deixa o conteúdo definir a altura
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(24.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(Color(0xFF00FF7F).copy(alpha = 0.1f))
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Recebidos",
                    tint = Color(0xFF00FF7F),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text("Recebidos", fontSize = 20.sp, color = Color.Gray)
            }

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.heightIn(max = 240.dp) // 👈 limite de altura com scroll
            ) {
                items(chats) { chat ->
                    ChatItemRecebidos(
                        chat = chat,
                        isSelected = chat == selectedChat,
                        onClick = { onSelect(chat) }
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
//                    .background(Color(0xFFFF4D4D).copy(alpha = 0.1f))
            ) {
                Icon(
                    imageVector = Icons.Default.Cancel,
                    contentDescription = "Não Recebidos",
                    tint = Color(0xFFFF4D4D),
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(12.dp))
                Text("Não Recebidos", fontSize = 20.sp, color = Color.Gray)
            }

            HorizontalDivider()

            LazyColumn(
                modifier = Modifier.heightIn(max = 240.dp) // 👈 limite de altura com scroll
            ) {
                items(chats) { chat ->
                    ChatItemNaoRecebidos(
                        chat = chat,
                        isSelected = chat == selectedChat,
                        onClick = { onSelect(chat) }
                    )
                }
            }
        }
    }
}






