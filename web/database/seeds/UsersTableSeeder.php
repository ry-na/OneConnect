<?php

use \Illuminate\Database\Seeder;

/**
 * @author Ryo Natori <ryo.natori0809@gmail.com>
 */
class UsersTableSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        factory(\App\Models\User::class)->create([
            \App\Models\User::EMAIL => 'ryo.natori0809@gmail.com',
            \App\Models\User::PASS => hash(config('app.hash'), 'userpass'),
        ]);
        factory(\App\Models\User::class)->create([
            \App\Models\User::EMAIL => 'a@gmail.com',
            \App\Models\User::PASS => hash(config('app.hash'), 'adada'),
        ]);
    }
}
