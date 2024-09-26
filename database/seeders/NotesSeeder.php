<?php

namespace Database\Seeders;

use Illuminate\Database\Seeder;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;
use Illuminate\Support\Str;

class NotesSeeder extends Seeder
{
    /**
     * Run the database seeds.
     *
     * @return void
     */
    public function run()
    {
        // Get existing user IDs
        $userIds = DB::table('users')->pluck('id')->toArray();

        if (empty($userIds)) {
            // If there are no users, output a message
            $this->command->info('No users found in the users table. Please add users first.');
            return;
        }

        // Seed the notes table
        for ($i = 0; $i < 20; $i++) {
            DB::table('notes')->insert([
            ]);
        }
    }
}
