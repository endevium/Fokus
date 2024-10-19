<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Factories\HasFactory;
use Illuminate\Database\Eloquent\Model;

class TaskHistory extends Model
{
    use HasFactory;

    // Define the table name if it's not the plural form of the model name
    protected $table = 'task_history'; // Optional if the table name is `task_histories`

    // Specify fillable attributes for mass assignment
    protected $fillable = [
        'task_id',
        'user_id',
        'status',
        'description',
    ];

    // Define relationships (if needed)
    public function task()
    {
        return $this->belongsTo(TaskModel::class);
    }

    public function user()
    {
        return $this->belongsTo(User::class); // Adjust if you have a specific User model
    }
}
