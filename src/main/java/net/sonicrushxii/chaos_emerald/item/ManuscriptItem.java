package net.sonicrushxii.chaos_emerald.item;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.sonicrushxii.chaos_emerald.capabilities.ChaosEmeraldProvider;
import net.sonicrushxii.chaos_emerald.capabilities.EmeraldType;
import net.sonicrushxii.chaos_emerald.network.PacketHandler;
import net.sonicrushxii.chaos_emerald.network.all.UpdateHandItem;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class ManuscriptItem extends Item {

    private CompoundTag generateAquaManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "Unknown Soldier");
        bookTag.putString("title", "Soldier's Report");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"Soldiers Report\\n\\nDuring a routine expedition to the surrounding islands, me and Mika found something very....odd. A stone, throne like structure with demonic engravings. I will admit, I was scared. But my stupid brother approached the damned thing and...\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"He vanished. I searched for hours, I couldn’t find him. It was getting dark, so I made my way back. Although on my way, I found another one of those gems the chief keeps talking about in some kind of container. With some difficulty I managed to take it. Its cyan glow is alluring, almost as if it’s...\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   §bo§0   <\\n x     x   \\nx   O   x   \\n x     x\"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }
    private CompoundTag generateBlueManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "Unknown Traveler");
        bookTag.putString("title", "Diary Entry");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"Dear Diary\\n\\nToday, I met a friend. I was out gathering berries, you know the usual routine. But today was different! I found a cute little guy floating around near the river fishing around in the stream for something. I approached him as anyone would do, and\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"he gave me the biggest smile! After a bit of gesturing, he showed me to his den. A small clearing in a cave full of shiny things! Rings, Crystals from the reef, even a weird blue gem I’ve never seen before. I’ve gotta tell the others about this little guy! I think I’ll name him chao, after his noises.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   x   <\\n x     x \\nx   O  §0§9o §9   \\n §0x     x     \"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }
    private CompoundTag generateGreenManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "Gisten");
        bookTag.putString("title", "The Cursed Rock");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"I can’t believe father executed another family. Just because they held some ancient artifact doesn’t justify murder! He got ahold of another one today, this time a green one. This madness can’t continue, tomorrow I’m going to try convincing him to stop killing without good\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"reason! This makes the 5th family killed in the name of some stupid god that doesn’t even exist! If my efforts are in vein, I’m leaving this documentation for someone who can stop my father. Kill him if you have to....he’s not my dad anymore. Just don’t let him hurt anyone else. -Tikal\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   x   <\\n x     x\\nx   O   x\\n §2o§0     x\"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }
    private CompoundTag generateGreyManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "The Elder");
        bookTag.putString("title", "Elder's Log");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"Well, this is a surprise.\\nOne of those chaos emeralds our \\\"lord\\\" is searching for was found in my garden this morning. There’s talk of me being some kind of traitor for it. Blasphemy! I’ve been this village’s loremaster for years! Have these children never been taught to show respect to their\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"elders? That power-hungry fool is going to get us all killed. From my porch I see some guards approaching my property. I have a very bad feeling this might be it for me. If you’re reading this friend, please....\\nStop this tyrant.\\nSave our people.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   §0x   <\\n x     x\\n§ §8o  §0 O   x\\n x     x\"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }
    private CompoundTag generatePurpleManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "Dear");
        bookTag.putString("title", "Mommy's Gift");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"Hey mom! I was picking lavender for you and dad this morning. I found this purple thing and I want you to have it. It glows pretty like you.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   x   <\\n §5o§0     x\\nx   O   x\\n x     x\"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }
    private CompoundTag generateRedManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "Thief");
        bookTag.putString("title", "Yellow Gem Theft");
        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"I can’t believe I did it! I snatched that stupid yellow rock off the podium when the guards weren’t looking!!! I’ve heard this thing grants unimaginable power to its wielder. But on closer inspection, it looks neat but it’s nothing special. Maybe it’s worth a lot on the\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"black market though. With the money I can finally buy my family out the slums for good! Wait... what was that sound?§k§4 §hoh no. §I§kits too late for me now, I’m sorry mother, forgive me.\\n\\n§0\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   x   <\\n x     §eo\\n§0x   O   x\\n x     x\"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }
    private CompoundTag generateYellowManuscript()
    {
        //Generate Text Tag for the Manuscript
        CompoundTag bookTag = new CompoundTag();

        //Custom Model Data, for Aesthetics
        bookTag.putInt("CustomModelData",221984);

        // Set author, title, and pages
        bookTag.putString("author", "Historian");
        bookTag.putString("title", "Echidna History");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"The Echidnas once worshipped the red emerald, believing that it was the source of all known power. This belief started a great civil war that ended with massive casualties, and a ruthless government that abused the power of the emeralds for selfish gain. Shortly after this age, the\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"master emerald was discovered, drawn towards the power exulted during the war. It soon became the center of a grand religion that unfortunately led to the tribe’s end.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\">   x   <\\n x     x\\nx   O   x \\n x     §co\"}"));

        // Attach the pages to the book tag
        bookTag.put("pages", pages);

        // Apply the NBT data to the ItemStack
        return bookTag;
    }

    private ItemStack getWrittenManuscript(Player pPlayer)
    {
        //Create Item Stack
        ItemStack writtenManuscript = new ItemStack(Items.WRITTEN_BOOK);

        //Set Custom Advancement
        Random random = new Random();
        byte index = (byte)random.nextInt(0,6);
        pPlayer.getCapability(ChaosEmeraldProvider.CHAOS_EMERALD_CAP).ifPresent(chaosEmeraldCap -> {
            //Can range from 0-6
            byte manuScriptType;

            //If empty, Set to true
            if(!chaosEmeraldCap.hasManuscript(index)) manuScriptType = index;
            //If not empty, keep looking for empty slot Until it loops around
            else {
                byte i;
                for(i = (byte) ((index+1)%7); chaosEmeraldCap.hasManuscript(i) && i != index; i= (byte)((i+1)%7));
                manuScriptType = i;
            }

            //Mark as Found(so a Player won't receive the same one twice)
            chaosEmeraldCap.foundManuscript(manuScriptType);

            switch(EmeraldType.values()[manuScriptType])
            {
                case AQUA_EMERALD: writtenManuscript.setTag(generateAquaManuscript()); break;
                case BLUE_EMERALD: writtenManuscript.setTag(generateBlueManuscript()); break;
                case GREEN_EMERALD: writtenManuscript.setTag(generateGreenManuscript()); break;
                case GREY_EMERALD: writtenManuscript.setTag(generateGreyManuscript()); break;
                case PURPLE_EMERALD: writtenManuscript.setTag(generatePurpleManuscript()); break;
                case RED_EMERALD: writtenManuscript.setTag(generateRedManuscript()); break;
                case YELLOW_EMERALD: writtenManuscript.setTag(generateYellowManuscript()); break;
            }

        });

        return writtenManuscript;
    }

    public ManuscriptItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        //Open the Manuscript
        pLevel.playSound(null,pPlayer.getX(),pPlayer.getY(),pPlayer.getZ(), SoundEvents.BOOK_PAGE_TURN, SoundSource.MASTER, 1.0f, 1.0f);

        //On R-Click Convert to Book
        if(!pLevel.isClientSide())
        {
            switch (pUsedHand) {
                case MAIN_HAND: pPlayer.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.WRITTEN_BOOK));
                                PacketHandler.sendToServer(new UpdateHandItem(getWrittenManuscript(pPlayer),InteractionHand.MAIN_HAND));
                                break;
                case OFF_HAND:  pPlayer.setItemSlot(EquipmentSlot.OFFHAND, new ItemStack(Items.WRITTEN_BOOK));
                                PacketHandler.sendToServer(new UpdateHandItem(getWrittenManuscript(pPlayer),InteractionHand.OFF_HAND));
                                break;
            }
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }
}
