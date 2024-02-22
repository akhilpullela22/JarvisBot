import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import net.dv8tion.jda.api.utils.cache.CacheFlag;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
public class Main extends ListenerAdapter {

    private ArrayList<TrackUser> CordUsers = new ArrayList<>();

    public static void main(String[] args){
        
        
        JDABuilder builder = JDABuilder.createDefault("token");
        builder.enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS);
        builder.disableCache(CacheFlag.MEMBER_OVERRIDES, CacheFlag.VOICE_STATE);
        builder.setBulkDeleteSplittingEnabled(false);
        builder.setActivity(Activity.watching("the server"));
        builder.addEventListeners(new Main());
        builder.build();
        
        
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("Received a message: " + event.getMessage().getContentRaw());

        String message = event.getMessage().getContentRaw();
        

        if (message.startsWith("!kickall ")) {
            String roleName = message.substring(9);  // get the role name from the message
            System.out.println("Role to kick: " + roleName);

            event.getGuild().loadMembers().onSuccess(members -> {
                Role role = event.getGuild().getRolesByName(roleName, true).get(0);
                List<Member> membersWithRole = members.stream()
                    .filter(member -> member.getRoles().contains(role))
                    .collect(Collectors.toList());
                for (Member member : membersWithRole) {
                    System.out.println("Kicking member: " + member.getUser().getName());
                    event.getGuild().kick(member).queue(null, error -> {
                        System.out.println("Failed to kick member: " + error.getMessage());
                    });
                }
            });
        }
        if (message.startsWith("!ult")){
            String[] parts = message.split(" ", 3);
            String task = parts[1];
            String[] nameAndUlt = parts[2].split(" ", 2);
            String name = nameAndUlt[0];
            String ult = "";
            if (nameAndUlt.length > 1) {
                ult = nameAndUlt[1];
}
            if (name.isBlank()){
                event.getChannel().sendMessage("No user given");
            }
            else{
                if (task.contains("adduser")){
                CordUsers.add(new TrackUser(name.strip()));
            }
            if (task.contains("addult")){
                if (ult.isBlank()){
                    event.getChannel().sendMessage("No ult given");
                }
                else{
                    for(TrackUser user : CordUsers){
                        if (user.getName().equals(name)){
                            user.addUlt(ult);
                        }
                    }

                }
            }
            if (task.contains("addpoint")){
                if (ult.isBlank()){
                    event.getChannel().sendMessage("No ult given");
                }
                else{
                    for(TrackUser user : CordUsers){
                        if (user.getName().equals(name)){
                            user.addPoint(ult);
                        }
                    }

                }

            }
            if (task.contains("removepoint")){
                if (ult.isBlank()){
                    event.getChannel().sendMessage("No ult given");
                }
                else{
                    for(TrackUser user : CordUsers){
                        if (user.getName().equals(name)){
                            user.removePoint(ult);
                        }
                    }

                }

            }
        }
        for (TrackUser user : CordUsers){
            System.out.println(user.getName());
            System.out.println(user.getUlts());
        }
        
            
            
            
        }

    
}
}

