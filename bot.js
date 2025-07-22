const { EmbedBuilder, Client, GatewayIntentBits } = require('discord.js');
const client = new Client({
  intents: [GatewayIntentBits.Guilds, GatewayIntentBits.GuildMessages, GatewayIntentBits.MessageContent]
});

const fs = require('fs');

const token = fs.readFileSync('C:\\Users\\akhil\\Documents\\Bot_keys\\bot_token.txt', 'utf8').trim();
const auth_key = fs.readFileSync('C:\\Users\\akhil\\Documents\\Bot_keys\\tba_auth.txt', 'utf8').trim();

client.on('ready', () => {
  console.log(`Logged in as ${client.user.tag}!`);
});

client.on('messageCreate', message => {
    // Bot prompted to provide status of API
    if (message.content.startsWith("!tba")) {
        fetch('https://www.thebluealliance.com/api/v3/status', {
        method: 'GET',
        headers: {
            'X-TBA-Auth-Key': auth_key,
            'Content-Type': 'application/json'
    }
    }).then(response => response.json()).then(data => {const jsonString = JSON.stringify(data);
            const parsed = JSON.parse(jsonString);
            // Check if API data feed is Online/Offline
            server_status = "Offline";
            if (parsed.is_datafeed_down == false){
                server_status = "Online";
            }
            const StatusEmbed = new EmbedBuilder()
            .setColor(0x0099FF)
            .setTitle('TBA API Status')
            .addFields(
                { name: "📅 **Current Season**", value: parsed.current_season.toString(), inline: false },
                { name: '📍 **Server Status**', value: server_status, inline: false }
            )
            .setFooter({ text: 'Data from The Blue Alliance' });

            message.channel.send({ embeds: [StatusEmbed] });
          })}
    
    // Bot prompted with team number
    if (message.content.startsWith("!team")) {
        const split_str = message.content.split(' ')
        const team_number = split_str[1]
        // Error handling for invalid team number (must be int)
        try{
            if (!/^\d+$/.test(team_number) || isNaN(Number(team_number))) {
                throw new Error("Invalid number");
            }
        }
        catch(error){
            message.channel.send("Invalid team number. Must be a whole number with digits only.")
        }
        // Create request to API and handle response data
        fetch('https://www.thebluealliance.com/api/v3/team/frc'+team_number, {
        method: 'GET',
        headers: {
            'X-TBA-Auth-Key': auth_key,
            'Content-Type': 'application/json'
    }
    })
    .then(response => response.json())
    .then(data => {
            const jsonString = JSON.stringify(data);
            const parsed = JSON.parse(jsonString);
            // Create embed and send message to the same channel as original prompt
            const TeamEmbed = new EmbedBuilder()
            .setColor(0x0099FF)
            .setTitle('Team ' + team_number)
            .addFields(
                {name : "🏫 **School**", value : parsed.school_name, inline : false}, 
                {name : '📍 **Location**', value : parsed.city + ', ' + parsed.state_prov + ', ' + parsed.country, inline : false}, 
                {name : '📅 **Established**', value : parsed.rookie_year.toString(), inline : false})
            .setFooter({ text: 'Data from The Blue Alliance' })
            .setTimestamp();
            message.channel.send({ embeds: [TeamEmbed] });
            })
    .catch(error => console.error('Error:', error));

    }


});

client.login(token);