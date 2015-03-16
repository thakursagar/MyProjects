using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace DeveloperTest.TreeClasses
{
    public class Input
    {
        public int Id { get; private set; }
        public int? ParentId { get; private set; }

        //parentId is optionally null
        public Input(int id, int? parentId = null)
        {
            Id = id;
            ParentId = parentId;
        }
    }
}
