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
        pages.add(StringTag.valueOf("{\"text\":\"Soldiers Report\\n\\nDuring a routine expedition of the nearby islands, Me and my brother, Mika discovered a giant stone structure engraved with ancient symbols. He tried to investigate, although against my better judgement.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"Suddenly, the fool touched the cursed circular symbol on the throne-like structure. I looked for him for hours, but he disappeared without a trace...\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"During my search however, I did discover a sky blue gem encased in a strange pillar. Out of pure instinct, I grabbed it from its containment, and escaped that cursed land.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"We can’t go back, it’s too dangerous. Even if it holds secrets, it’s not worth exchanging our blood for its knowledge.\\n\\n      (-)\\n X         X\\nX    0     X\\n X         X\"}"));

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
        pages.add(StringTag.valueOf("{\"text\":\"Dear diary, today I met a friend while gathering berries. A little creature approached me and began leading me towards a small clearing deeper in the woods.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"Inside, I found what appeared to be its nest. Plenty of shiny things strewn about, including some kind of blue, shiny rock. I’ll have to tell the tribe about this!\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"It’s so cute, I think I’ll name him Chao after the noise he makes!\\n\\n       X\\n X         X\\nX    0    (-)\\n X         X\"}"));

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
        pages.add(StringTag.valueOf("{\"text\":\"I can’t believe the chief executed an entire family out of nowhere! And we are expected to just, accept that?! All for some stupid rock too!\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"Apparently anyone who harbors one of these weird things ends up either missing or dead! These things are cursed! They have six of them in possession, according to a friend.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"I decided to swipe the green rock when they weren’t looking. I don’t have a family, so I’m going to run and never look back.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"I know about a bright green ancient palace, completely closed off to the outside world. I think if I can make it there, I can foil whatever the higher ups are planning.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"I’m sorry Tikal, forgive me for abandoning you. I hope you understand.....I love you.\\n~Gisten\\n\\n       X\\n X         X\\nX    0     X\\n(-)        X\"}"));

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
        pages.add(StringTag.valueOf("{\"text\":\"Elders log:\\n\\nToday became quite interesting. I was tending to my garden when suddenly, a small silver rock fell from the heavens, nearly caving my skull in!\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"It was almost like it was escaping desperately from something. I’ve never quite seen anything like it. So beautiful and smooth.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"I think I’m going to keep it as a decoration. Maybe its beauty will influence people to elect me as the next lore keeper! Hohoho!\\n\\n       X\\n(-)        X\\nX    0     X\\n X         X\"}"));

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
        pages.add(StringTag.valueOf("{\"text\":\"Hi mommy! I was picking flowers today. I found a weird purple thing. I want you to have it. I love you mommy!\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"\\n\\n       X\\n X         X\\n(-)  0     X\\n X         X\"}"));

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
        bookTag.putString("author", "Historian");
        bookTag.putString("title", "Echidna History");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"The echidnas once worshipped the red emerald, believing it was the source of all known power in this world.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"This belief started a small civil war that unfortunately ended in tragedy. During this age, the master emerald was discovered during an expedition into uncharted lands.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"\\n       X\\n X         X\\nX    0     X\\n X        (-)\"}"));

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
        bookTag.putString("author", "Thief");
        bookTag.putString("title", "Yellow Gem Theft");

        // Create a ListTag for the pages
        ListTag pages = new ListTag();
        // Add the JSON text to the pages
        pages.add(StringTag.valueOf("{\"text\":\"I can’t believe I did it. I stole the yellow gem! It’s all mine! I don’t care about that hokey religion stuff either!\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"I’m going to hide it in my den. I love the way it sparkles....it’s so, enchanting. Almost dangerously so.\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"Holding it makes me feel....powerful. Even if the guards find out, I think I can take them! I can......(Illegible text)\"}"));
        pages.add(StringTag.valueOf("{\"text\":\"\\n\\n \\n       X\\n X        (-)\\nX    0     X\\n X         X\"}"));

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

            switch(manuScriptType)
            {
                case 0: writtenManuscript.setTag(generateAquaManuscript()); break;
                case 1: writtenManuscript.setTag(generateBlueManuscript()); break;
                case 2: writtenManuscript.setTag(generateGreenManuscript()); break;
                case 3: writtenManuscript.setTag(generateGreyManuscript()); break;
                case 4: writtenManuscript.setTag(generatePurpleManuscript()); break;
                case 5: writtenManuscript.setTag(generateRedManuscript()); break;
                case 6: writtenManuscript.setTag(generateYellowManuscript()); break;
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
