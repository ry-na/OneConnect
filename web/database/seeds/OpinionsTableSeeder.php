<?php

use Illuminate\Database\Seeder;

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class OpinionsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table(\App\Models\Opinion::TABLE)->truncate();
        $testData = [
            [
                \App\Models\Opinion::USER_ID => 1,
                \App\Models\Opinion::EVENT_ID => null,
                \App\Models\Opinion::OPINION_MESSAGE => "テスト01メッセージ",
                \App\Models\Opinion::LAT => 35.689634,
                \App\Models\Opinion::LON => 139.692100
            ],
            [
                \App\Models\Opinion::USER_ID => 2,
                \App\Models\Opinion::EVENT_ID => null,
                \App\Models\Opinion::OPINION_MESSAGE => "テスト02メッセージ",
                \App\Models\Opinion::LAT => 35.660171,
                \App\Models\Opinion::LON => 139.729175
            ]
        ];
        foreach ($testData as $data) {
            \App\Models\Opinion::create($data);
        }
    }
}
