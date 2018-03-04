<?php

use \Illuminate\Database\Seeder;

/**
 * @author Takahiro Otani <taka.oot@gmail.com>
 */
class SessionsTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        DB::table(\App\Models\Session::TABLE)->truncate();
    }
}
