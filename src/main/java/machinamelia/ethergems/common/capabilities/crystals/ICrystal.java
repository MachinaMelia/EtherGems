package machinamelia.ethergems.common.capabilities.crystals;

/*
 *   Copyright (C) 2020 MachinaMelia
 *
 *    This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.
 *
 *    This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License for more details.
 *
 *    You should have received a copy of the GNU Lesser General Public License along with this library. If not, see <http://www.gnu.org/licenses/>.
 */

public interface ICrystal {
    public String getAttributesCSV();
    public int[] getStrengthArray();
    public String[] getShuffledAttributes();
    public void setAttributesCSV(String attributes);
    public void setStrengthArray(int[] strengths);
    public String getAttributesTooltip();
    public String getElement();
    public int getLevel();
    public void setLevel(int level);
    public void init();
}
