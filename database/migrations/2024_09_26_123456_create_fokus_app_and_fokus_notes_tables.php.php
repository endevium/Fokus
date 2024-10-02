<?php

use Illuminate\Database\Migrations\Migration;
use Illuminate\Database\Schema\Blueprint;
use Illuminate\Support\Facades\Schema;

class CreateFokusAppAndFokusNotesTables extends Migration
{
    /**
     * Run the migrations.
     */
    public function up(): void
    {
        // Check if 'fokus_app' table exists before creating it
        if (!Schema::hasTable('fokus_app')) {
            Schema::create('fokus_app', function (Blueprint $table) {
                $table->id();
                $table->string('username')->unique();
                $table->string('password');
                $table->string('email')->unique();
                $table->timestamps();
            });
        }

        // Check if 'fokus_notes' table exists before creating it
        if (!Schema::hasTable('fokus_notes')) {
            Schema::create('fokus_notes', function (Blueprint $table) {
                $table->id();
                $table->foreignId('fokus_app_id')->constrained('fokus_app')->onDelete('cascade'); // Foreign key reference
                $table->string('title');
                $table->text('content');
                $table->timestamps();
            });
        }

        // Check if 'fokus_task' table exists before creating it
        if (!Schema::hasTable('fokus_task')) {
            Schema::create('fokus_task', function (Blueprint $table) {
                $table->id(); // Task ID
                $table->foreignId('fokus_app_id')->constrained('fokus_app')->onDelete('cascade'); // Link to the user
                $table->string('token')->unique(); // Add token column
                $table->string('task_title'); // Added task_title as it was missing
            });
        }
    }

    /**
     * Reverse the migrations.
     */
    public function down(): void
    {
        // Drop tables in the reverse order they were created
        Schema::dropIfExists('fokus_task');
        Schema::dropIfExists('fokus_notes');
        Schema::dropIfExists('fokus_app');
    }
}
