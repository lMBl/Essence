/*
 * This file is part of the L2J Mobius project.
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package org.l2jmobius.gameserver.network.serverpackets.newskillenchant;

import org.l2jmobius.commons.network.WritableBuffer;
import org.l2jmobius.gameserver.data.xml.SkillEnchantData;
import org.l2jmobius.gameserver.model.ItemInfo;
import org.l2jmobius.gameserver.model.actor.Player;
import org.l2jmobius.gameserver.model.holders.EnchantStarHolder;
import org.l2jmobius.gameserver.model.item.instance.Item;
import org.l2jmobius.gameserver.model.itemcontainer.Inventory;
import org.l2jmobius.gameserver.model.skill.Skill;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.ServerPackets;
import org.l2jmobius.gameserver.network.serverpackets.AbstractItemPacket;

/**
 * @author Serenitty
 */
public class ExSkillEnchantInfo extends AbstractItemPacket
{
	private final Skill _skill;
	private final Player _player;
	private final EnchantStarHolder _starHolder;
	
	public ExSkillEnchantInfo(Skill skill, Player player)
	{
		_skill = skill;
		_player = player;
		_starHolder = SkillEnchantData.getInstance().getEnchantStar(SkillEnchantData.getInstance().getSkillEnchant(skill.getId()).getStarLevel());
	}
	
	@Override
	public void writeImpl(GameClient client, WritableBuffer buffer)
	{
		ServerPackets.EX_SKILL_ENCHANT_INFO.writeId(this, buffer);
		buffer.writeInt(_skill.getId());
		buffer.writeInt(_skill.getSubLevel());
		buffer.writeInt(_player.getSkillEnchantExp(_starHolder.getLevel()));
		buffer.writeInt(_starHolder.getExpMax());
		buffer.writeInt(SkillEnchantData.getInstance().getChanceEnchantMap(_skill) * 100);
		buffer.writeShort(calculatePacketSize(new ItemInfo(new Item(Inventory.ADENA_ID))));
		buffer.writeInt(Inventory.ADENA_ID);
		buffer.writeLong(1000000);
	}
}
