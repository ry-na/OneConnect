<?php

use Illuminate\Database\Seeder;

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class RepliesTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table(\App\Models\Reply::TABLE)->truncate();
        $testData = [
            [
                \App\Models\Reply::USER_ID => 2,
                \App\Models\Reply::OPINION_ID => 0,
                \App\Models\Reply::REPLY_MESSAGE => "返信01メッセージ"
            ],
            [
                \App\Models\Reply::USER_ID => 1,
                \App\Models\Reply::OPINION_ID => 1,
                \App\Models\Reply::REPLY_MESSAGE => "返信02メッセージ"
            ],
            [
                \App\Models\Reply::USER_ID => 2,
                \App\Models\Reply::OPINION_ID => 1,
                \App\Models\Reply::REPLY_MESSAGE => "返信03メッセージ"
            ]
        ];
        foreach ($testData as $data) {
            \App\Models\Reply::create($data);
        }
    }
}
