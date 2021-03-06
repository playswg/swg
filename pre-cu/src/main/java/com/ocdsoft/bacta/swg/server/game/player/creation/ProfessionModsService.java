package com.ocdsoft.bacta.swg.server.game.player.creation;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.ocdsoft.bacta.swg.server.game.object.tangible.creature.Attribute;
import com.ocdsoft.bacta.swg.shared.datatable.DataTable;
import com.ocdsoft.bacta.swg.shared.datatable.DataTableManager;
import gnu.trove.impl.unmodifiable.TUnmodifiableIntList;
import gnu.trove.list.TIntList;
import gnu.trove.list.array.TIntArrayList;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by crush on 3/28/14.
 */
@Singleton
public final class ProfessionModsService {
    private static final String DATA_TABLE_NAME = "datatables/creation/profession_mods.iff";
    private static final Logger LOGGER = LoggerFactory.getLogger(ProfessionModsService.class);

    private Map<String, ProfessionModInfo> professionMods;

    private final DataTableManager dataTableManager;

    @Inject
    public ProfessionModsService(final DataTableManager dataTableManager) {
        this.dataTableManager = dataTableManager;
        load();
    }

    /**
     * Gets a {@link ProfessionModInfo} object for the
     * specified profession.
     *
     * @param profession The profession is a string. i.e. combat_marksman, crafting_artisan, etc.
     * @return {@link ProfessionModInfo} if the key exists.
     * Otherwise, null.
     */
    public ProfessionModInfo getProfessionModInfo(final String profession) {
        return professionMods != null ? professionMods.get(profession) : null;
    }

    private void load() {
        LOGGER.trace("Loading profession mods.");

        final DataTable dataTable = dataTableManager.getTable(DATA_TABLE_NAME, true);

        if (dataTable != null) {
            final Map<String, ProfessionModInfo> map = new HashMap<>(dataTable.getNumRows());

            for (int row = 0; row < dataTable.getNumRows(); ++row) {
                final ProfessionModInfo modInfo = new ProfessionModInfo(dataTable, row);
                map.put(modInfo.profession, modInfo);
            }

            professionMods = ImmutableMap.copyOf(map);
            dataTableManager.close(DATA_TABLE_NAME);
        }

        LOGGER.debug(String.format("Loaded %d profession mods.", professionMods != null ? professionMods.size() : 0));
    }


    @Getter
    public static final class ProfessionModInfo {
        private final String profession;
        private final TIntList attributes;

        public ProfessionModInfo(final DataTable dataTable, final int row) {
            profession = dataTable.getStringValue("profession", row);

            final TIntList list = new TIntArrayList(Attribute.SIZE);
            list.add(dataTable.getIntValue("health", row));
            list.add(dataTable.getIntValue("constitution", row));
            list.add(dataTable.getIntValue("action", row));
            list.add(dataTable.getIntValue("stamina", row));
            list.add(dataTable.getIntValue("mind", row));
            list.add(dataTable.getIntValue("willpower", row));

            attributes = new TUnmodifiableIntList(list);
        }
    }
}
